package acme.features.authenticated.fundraiser.strategy;

import acme.client.helpers.MessageHelper;

public final class FundraiserStrategyI18nHelper {

	private static final String	DRAFT_MODE_TRUE_CODE	= "authenticated.strategy.boolean.true";
	private static final String	DRAFT_MODE_FALSE_CODE	= "authenticated.strategy.boolean.false";

	private FundraiserStrategyI18nHelper() {
		;
	}

	public static String draftModeDisplay(final Boolean draftMode) {
		String result;

		result = Boolean.TRUE.equals(draftMode) ? MessageHelper.getMessage(FundraiserStrategyI18nHelper.DRAFT_MODE_TRUE_CODE) : MessageHelper.getMessage(FundraiserStrategyI18nHelper.DRAFT_MODE_FALSE_CODE);

		return result;
	}
}
