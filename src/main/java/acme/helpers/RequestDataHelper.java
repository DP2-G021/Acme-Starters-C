package acme.helpers;

import acme.client.components.models.Request;
import acme.client.helpers.StringHelper;

public abstract class RequestDataHelper {

	// Constructors -----------------------------------------------------------

	protected RequestDataHelper() {
	}

	// Business methods -------------------------------------------------------

	public static Integer getNaturalIntegerParameter(final Request request, final String key) {
		assert request != null;
		assert !StringHelper.isBlank(key);

		Integer result;
		String rawValue, trimmedValue;

		rawValue = request.getData(key, String.class, null);
		if (StringHelper.isBlank(rawValue))
			result = null;
		else {
			trimmedValue = rawValue.trim();

			try {
				result = Integer.valueOf(trimmedValue);
			} catch (final NumberFormatException oops) {
				result = null;
			}
		}

		return result;
	}
}
