package model.filters;

/**
 * This enum is used to store all the filters that are available to the user. It is used to
 * create a list of filters that can be used in the program.
 */

public enum FiltersEnum {
  REDFILTER(new RedFilter()),
  BLUEFILTER(new BlueFilter()),
  GREENFILTER(new GreenFilter()),
  LUMABRIGHTEN(new LumaBrighten()),
  LUMADARKEN(new LumaDarken()),
  INTENSITYBRIGHTEN(new IntensityBrighten()),
  INTENSITYDARKEN(new IntensityDarken()),
  VALUEBRIGHTEN(new ValueBrighten()),
  VALUEDARKEN(new ValueDarken()),
  NORMALFILTER(new Normal()),
  DIFFERENCEFILTER(new DifferenceFilter()),
  MULTIPLYFILTER(new MultiplyFilter()),
  SCREENFILTER(new ScreenFilter());


  private final IFilter filter;

  /**
   * Sets the filter to a given filter.
   *
   * @param filter provided filter.
   */
  FiltersEnum(IFilter filter) {
    this.filter = filter;
  }

  /**
   * Returns the filter.
   *
   * @return filter of a filter.
   */

  public IFilter getFilter() {
    return this.filter;
  }


}
