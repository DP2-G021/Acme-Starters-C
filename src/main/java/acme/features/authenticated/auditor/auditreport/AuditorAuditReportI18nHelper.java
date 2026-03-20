package acme.features.authenticated.auditor.auditreport;

import acme.client.helpers.MessageHelper;

public final class AuditorAuditReportI18nHelper {

	private static final String	DRAFT_MODE_TRUE_CODE	= "authenticated.audit-report.boolean.true";
	private static final String	DRAFT_MODE_FALSE_CODE	= "authenticated.audit-report.boolean.false";

	private AuditorAuditReportI18nHelper() {
		;
	}

	public static String draftModeDisplay(final Boolean draftMode) {
		String result;

		result = Boolean.TRUE.equals(draftMode) ? MessageHelper.getMessage(AuditorAuditReportI18nHelper.DRAFT_MODE_TRUE_CODE) : MessageHelper.getMessage(AuditorAuditReportI18nHelper.DRAFT_MODE_FALSE_CODE);

		return result;
	}
}
