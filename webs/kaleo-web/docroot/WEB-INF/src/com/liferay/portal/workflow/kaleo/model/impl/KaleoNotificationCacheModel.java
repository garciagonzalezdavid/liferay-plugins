/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;

import java.util.Date;

/**
 * The cache model class for representing KaleoNotification in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotification
 * @generated
 */
public class KaleoNotificationCacheModel implements CacheModel<KaleoNotification> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{kaleoNotificationId=");
		sb.append(kaleoNotificationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
		sb.append(", kaleoNodeId=");
		sb.append(kaleoNodeId);
		sb.append(", kaleoNodeName=");
		sb.append(kaleoNodeName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", executionType=");
		sb.append(executionType);
		sb.append(", template=");
		sb.append(template);
		sb.append(", templateLanguage=");
		sb.append(templateLanguage);
		sb.append(", notificationTypes=");
		sb.append(notificationTypes);
		sb.append("}");

		return sb.toString();
	}

	public KaleoNotification toEntityModel() {
		KaleoNotificationImpl kaleoNotificationImpl = new KaleoNotificationImpl();

		kaleoNotificationImpl.setKaleoNotificationId(kaleoNotificationId);
		kaleoNotificationImpl.setGroupId(groupId);
		kaleoNotificationImpl.setCompanyId(companyId);
		kaleoNotificationImpl.setUserId(userId);

		if (userName == null) {
			kaleoNotificationImpl.setUserName(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setUserName(userName);
		}

		if (createDate > 0) {
			kaleoNotificationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate > 0) {
			kaleoNotificationImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoNotificationImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoNotificationImpl.setKaleoNodeId(kaleoNodeId);

		if (kaleoNodeName == null) {
			kaleoNotificationImpl.setKaleoNodeName(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setKaleoNodeName(kaleoNodeName);
		}

		if (name == null) {
			kaleoNotificationImpl.setName(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setName(name);
		}

		if (description == null) {
			kaleoNotificationImpl.setDescription(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setDescription(description);
		}

		if (executionType == null) {
			kaleoNotificationImpl.setExecutionType(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setExecutionType(executionType);
		}

		if (template == null) {
			kaleoNotificationImpl.setTemplate(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setTemplate(template);
		}

		if (templateLanguage == null) {
			kaleoNotificationImpl.setTemplateLanguage(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setTemplateLanguage(templateLanguage);
		}

		if (notificationTypes == null) {
			kaleoNotificationImpl.setNotificationTypes(StringPool.BLANK);
		}
		else {
			kaleoNotificationImpl.setNotificationTypes(notificationTypes);
		}

		kaleoNotificationImpl.resetOriginalValues();

		return kaleoNotificationImpl;
	}

	public long kaleoNotificationId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
	public long kaleoNodeId;
	public String kaleoNodeName;
	public String name;
	public String description;
	public String executionType;
	public String template;
	public String templateLanguage;
	public String notificationTypes;
}