
package acme.features.authenticated.sponsor.sponsorship;

import acme.client.helpers.MessageHelper;

public final class SponsorSponsorshipI18nHelper {

	private static final String	DRAFT_MODE_TRUE_CODE	= "authenticated.sponsorship.boolean.true";
	private static final String	DRAFT_MODE_FALSE_CODE	= "authenticated.sponsorship.boolean.false";


	private SponsorSponsorshipI18nHelper() {
		;
	}

	public static String draftModeDisplay(final Boolean draftMode) {
		String result;

		result = Boolean.TRUE.equals(draftMode) ? MessageHelper.getMessage(SponsorSponsorshipI18nHelper.DRAFT_MODE_TRUE_CODE) : MessageHelper.getMessage(SponsorSponsorshipI18nHelper.DRAFT_MODE_FALSE_CODE);

		return result;
	}
}
