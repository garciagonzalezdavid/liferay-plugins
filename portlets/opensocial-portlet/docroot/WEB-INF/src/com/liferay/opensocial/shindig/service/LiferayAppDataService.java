/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.opensocial.shindig.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.apache.shindig.auth.SecurityToken;
import org.apache.shindig.common.util.ImmediateFuture;
import org.apache.shindig.protocol.DataCollection;
import org.apache.shindig.protocol.ProtocolException;
import org.apache.shindig.social.opensocial.spi.AppDataService;
import org.apache.shindig.social.opensocial.spi.GroupId;
import org.apache.shindig.social.opensocial.spi.UserId;

/**
 * <a href="LiferayAppDataService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 */
public class LiferayAppDataService implements AppDataService {

	public Future<Void> deletePersonData(
			UserId userId, GroupId groupId, String appId, Set<String> fields,
			SecurityToken securityToken)
		throws ProtocolException {

		try {
			doDeletePersonData(userId, groupId, appId, fields, securityToken);

			return ImmediateFuture.newInstance(null);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new ProtocolException(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(),
				e);
		}
	}

	public Future<DataCollection> getPersonData(
			Set<UserId> userIds, GroupId groupId, String appId,
			Set<String> fields, SecurityToken securityToken)
		throws ProtocolException {

		try {
			DataCollection dataCollection = doGetPersonData(
				userIds, groupId, appId, fields, securityToken);

			return ImmediateFuture.newInstance(dataCollection);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new ProtocolException(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(),
				e);
		}
	}

	public Future<Void> updatePersonData(
			UserId userId, GroupId groupId, String appId, Set<String> fields,
			Map<String, String> values, SecurityToken securityToken)
		throws ProtocolException {

		try {
			updatePersonData(
				userId, groupId, appId, fields, values, securityToken);

			return ImmediateFuture.newInstance(null);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new ProtocolException(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(),
				e);
		}
	}

	protected void doDeletePersonData(
			UserId userId, GroupId groupId, String appId, Set<String> fields,
			SecurityToken securityToken)
		throws Exception {

		long companyId = getCompanyId(securityToken);

		long userIdLong = GetterUtil.getLong(userId.getUserId(securityToken));

		for (String field : fields) {
			ExpandoColumn expandoColumn = getExpandoColumn(companyId, field);

			ExpandoValueLocalServiceUtil.deleteValue(
				companyId, User.class.getName(), OPEN_SOCIAL_DATA,
				expandoColumn.getName(), userIdLong);
		}

		return;
	}

	protected DataCollection doGetPersonData(
			Set<UserId> userIds, GroupId groupId, String appId,
			Set<String> fields, SecurityToken securityToken)
		throws Exception {

		long companyId = getCompanyId(securityToken);

		Map<String, Map<String, String>> peopleAppData =
			new HashMap<String, Map<String, String>>();

		for (UserId userId : userIds) {
			String userIdString = userId.getUserId(securityToken);

			long userIdLong = GetterUtil.getLong(userIdString);

			Map<String, String> personAppData = new HashMap<String, String>();

			for (String field : fields) {
				String value = getExpandoValue(companyId, userIdLong, field);

				personAppData.put(field, value);
			}

			peopleAppData.put(userIdString, personAppData);
		}

		DataCollection dataCollection = null;

		return dataCollection;
	}

	protected void doUpdatePersonData(UserId userId, GroupId groupId,
			String appId, Set<String> fields, Map<String, String> values,
			SecurityToken securityToken)
		throws Exception {

		long companyId = getCompanyId(securityToken);

		long userIdLong = GetterUtil.getLong(userId.getUserId(securityToken));

		for (String field : fields) {
			ExpandoColumn expandoColumn = getExpandoColumn(companyId, field);

			ExpandoValueLocalServiceUtil.addValue(
				companyId, User.class.getName(), OPEN_SOCIAL_DATA,
				expandoColumn.getName(), userIdLong, values.get(field));
		}

		return;
	}

	protected long getCompanyId(SecurityToken securityToken) throws Exception {
		long userId = GetterUtil.getLong(securityToken.getViewerId());

		User user = UserLocalServiceUtil.getUser(userId);

		return user.getCompanyId();
	}

	protected ExpandoColumn getExpandoColumn(long companyId, String columnName)
		throws Exception {

		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, User.class.getName(), OPEN_SOCIAL_DATA);
		}
		catch (NoSuchTableException nste) {
			expandoTable = ExpandoTableLocalServiceUtil.addTable(
				companyId, User.class.getName(), OPEN_SOCIAL_DATA);
		}

		ExpandoColumn expandoColumn = null;

		try {
			expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(
				expandoTable.getTableId(), columnName);
		}
		catch (NoSuchColumnException nsce) {
			expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), columnName,
				ExpandoColumnConstants.STRING);
		}

		return expandoColumn;
	}

	protected String getExpandoValue(
		long companyId, long userId, String columnName) {

		try {
			ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.getValue(
				companyId, User.class.getName(), OPEN_SOCIAL_DATA, columnName,
				userId);

			return expandoValue.getData();
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	private static final String OPEN_SOCIAL_DATA = "OPEN_SOCIAL_DATA";

	private static Log _log = LogFactoryUtil.getLog(
		LiferayAppDataService.class);

}