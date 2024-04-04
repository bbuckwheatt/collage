package model.filters;


/**
 * Abstract class for filters. The parent class for all filter classes that do/will exist.
 */
public abstract class Filter implements IFilter {
  String name;

  /**
   * Gets the name of the given filter.
   *
   * @return returns the filter name.
   */
  public String getName() {
    return this.name;
  }
}
