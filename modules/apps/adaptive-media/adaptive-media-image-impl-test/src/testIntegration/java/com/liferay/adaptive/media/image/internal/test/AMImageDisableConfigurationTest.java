/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.adaptive.media.image.internal.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class AMImageDisableConfigurationTest
	extends BaseAMImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testDisableAllConfigurationEntries() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");
		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertDisabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertDisabled(secondAMImageConfigurationEntryOptional);
	}

	@Test
	public void testDisableConfigurationWithExistingDisabledConfiguration()
		throws Exception {

		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		assertDisabled(amImageConfigurationEntryOptional);
	}

	@Test
	public void testDisableDisabledConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);
	}

	@Test
	public void testDisableFirstConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertDisabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);
	}

	@Test
	public void testDisableNonExistantConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		String uuid = StringUtil.randomString();

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), uuid);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), uuid);

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDisableSecondConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertDisabled(secondAMImageConfigurationEntryOptional);
	}

	@Test
	public void testDisableUniqueConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);
	}

	@Test
	public void testSendsAMessageToTheMessageBus() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		List<Message> messages = collectConfigurationMessages(
			() -> amImageConfigurationHelper.disableAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1"));

		Assert.assertEquals(messages.toString(), 1, messages.size());

		Message message = messages.get(0);

		AMImageConfigurationEntry amImageConfigurationEntry =
			(AMImageConfigurationEntry)message.getPayload();

		Assert.assertEquals("one", amImageConfigurationEntry.getName());
		Assert.assertEquals(
			"onedesc", amImageConfigurationEntry.getDescription());
		Assert.assertEquals("1", amImageConfigurationEntry.getUUID());
		Assert.assertEquals(
			properties, amImageConfigurationEntry.getProperties());
	}

}