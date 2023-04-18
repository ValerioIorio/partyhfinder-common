package com.partyh.finder.common.models;

public abstract class AbstractFilter {
    protected Integer pageSize;
    protected Integer numberOfPage;
    protected Boolean descending;
    protected String sortBy;

    public AbstractFilter(Integer pageSize, Integer numberOfPage, Boolean descending, String sortBy) {
        this.pageSize = pageSize;
        this.numberOfPage = numberOfPage;
        this.descending = descending;
        this.sortBy = sortBy;
    }
   /**
    * Method to check if the sortBy field is valid
    * @param validSortBy the array of valid sortBy fields
    * @return true if the sortBy field is valid or if the field is null, false otherwise
    * */
    public Boolean checkValidSortBy(String[] validSortBy) {
        for(String valid : validSortBy) {
            if(valid.equals(this.sortBy)) {
                return true;
            }
        }
        return this.sortBy == null;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getNumberOfPage() {
        return numberOfPage;
    }

    public Boolean getDescending() {
        return descending;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setNumberOfPage(Integer numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
