package products;

import java.util.HashSet;
import java.util.Set;

/**
 * A Book is made up of the fields describing facts about the item.
 *
 */
public class Book implements Item
{
	String title;
	HashSet<String> authors;
	String editor;
	String forewordBy;
	String introductionBy;
	String priceStr;
	String isbn_10;
	String isbn_13;
	String publisher;
	String edition;
	String releaseDateStr;
	String format;
	String pageNumStr;
	String language;
	String ageRange;
	String dimensionStr;
	Float weightValue;
	String weightUnit;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Set<String> getAuthors() {
		return authors;
	}
	public void setAuthors(HashSet<String> authors) {
		this.authors = authors;
	}
	public boolean addAuthor(String name){
		if (authors==null){authors = new HashSet<String>();}
		return this.authors.add(name);
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getForewordBy() {
		return forewordBy;
	}
	public void setForewordBy(String forewordBy) {
		this.forewordBy = forewordBy;
	}
	public String getIntroductionBy() {
		return introductionBy;
	}
	public void setIntroductionBy(String introductionBy) {
		this.introductionBy = introductionBy;
	}
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	public String getIsbn_10() {
		return isbn_10;
	}
	public void setIsbn_10(String isbn_10) {
		this.isbn_10 = isbn_10;
	}
	public String getIsbn_13() {
		return isbn_13;
	}
	public void setIsbn_13(String isbn_13) {
		this.isbn_13 = isbn_13;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange =ageRange;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getPageNumStr() {
		return pageNumStr;
	}
	public void setPageNumStr(String pageNumStr) {
		this.pageNumStr = pageNumStr;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDimensionStr() {
		return dimensionStr;
	}
	public void setDimensionStr(String dimensionStr) {
		this.dimensionStr = dimensionStr;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnitc(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public Float getWeightValue() {
		if (weightValue==null)
			return DEFAULT_UNKNOWN_WEIGHT;
		return weightValue;
	}
	public void setWeightValue(float weightValue) {
		this.weightValue = weightValue;
	}
	public String getWeightStr() {
		return getWeightValue()+" "+(weightUnit!=null?getWeightUnit():"");
	}
	
	public String printItem() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("Title : "+(title!=null?title:"")+"\n");
		if (authors!=null)
		for (String author:authors)
			sb.append("Author : "+(author!=null?author:"")+"\n");
		else sb.append("Author : \n");
		sb.append("Editor : "+(editor!=null?editor:"")+"\n");
		sb.append("Foreword : "+(forewordBy!=null?forewordBy:"")+"\n");
		sb.append("Introduction : "+(introductionBy!=null?introductionBy:"")+"\n");
		sb.append("Price : "+(priceStr!=null?priceStr:"")+"\n");	
		sb.append("ISBN-10 : "+(isbn_10!=null?isbn_10:"")+"\n");	
		sb.append("ISBN-13 : "+(isbn_13!=null?isbn_13:"")+"\n");	
		sb.append("Edition : "+(edition!=null?edition:"")+"\n");	
		sb.append("Publisher : "+(publisher!=null?publisher:"")+"\n");	
		sb.append("Release Date : "+(releaseDateStr!=null?releaseDateStr:"")+"\n");	
		sb.append("Format : "+(format!=null?format:"")+"\n");	
		sb.append("Pages : "+(pageNumStr!=null?pageNumStr:"")+"\n");	
		sb.append("Language : "+(language!=null?language:"")+"\n");	
		sb.append("Age Range : "+(ageRange!=null?ageRange:"")+"\n");	
		sb.append("Dimensions : "+(dimensionStr!=null?dimensionStr:"")+"\n");	
		sb.append("Shipping Weight : "+this.getWeightStr()+"\n");	
		sb.append("}\n");
		return sb.toString();
	}
}
