
package acme.features.authenticated.spokesperson.campaign;

import acme.client.helpers.MessageHelper;

public class SpokespersonCampaignI18nHelper {

	private static final String	DRAFT_MODE_TRUE_CODE	= "authenticated.campaign.boolean.true";
	private static final String	DRAFT_MODE_FALSE_CODE	= "authenticated.campaign.boolean.false";


	private SpokespersonCampaignI18nHelper() {
		;
	}

	public static String draftModeDisplay(final Boolean draftMode) {
		String result;

		result = Boolean.TRUE.equals(draftMode) ? MessageHelper.getMessage(SpokespersonCampaignI18nHelper.DRAFT_MODE_TRUE_CODE) : MessageHelper.getMessage(SpokespersonCampaignI18nHelper.DRAFT_MODE_FALSE_CODE);

		return result;
	}

}
