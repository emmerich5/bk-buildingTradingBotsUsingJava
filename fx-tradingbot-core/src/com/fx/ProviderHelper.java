package com.fx;

/**
 * 
 * Long/Short notation for alias of pairs from several sources
 * Vocabulary:
 *   * instrument
 *   * platform
 *
 * @param <T>
 */
public interface ProviderHelper<T> {
	
	/**
	 * 
	 * @param instrument
	 * @return currency pair denoted in ISO currency standard (Example: GBPUSD)
	 */
	String fromIsoFormat(String instrument);
	
	String toIsoFormat(String instrument);
	
	String fromPairSeparatorFormat(String instrument);
	
	String fromHashTagCurrency(String instrument);
	
	/**
	 * 
	 * @return the action of Buying the currency pair on the platform.
	 */
	T getLongNotation();
	
	/**
	 * 
	 * @return the action of Selling the currency pair on the platform.
	 */
	T getShortNotation();
}
