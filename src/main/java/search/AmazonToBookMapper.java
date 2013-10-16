package search;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import products.Book;

/**
 * This class has a collection of methods used to extract details from an Amazon
 * book listing.
 * 
 */
public class AmazonToBookMapper {

	/**
	 * Method attempts to populate a book's price from JSOUP document
	 * @param doc
	 * @param blank
	 */
	public static void fillBookPrice(Document doc, Book blank) {
		if (doc == null)
			return;

		Elements docMatches = doc.select(".rbb_header");
		if (docMatches == null || docMatches.isEmpty())
			return;

		Element priceTag = docMatches.first();
		if (priceTag == null)
			return;

		Elements prices = priceTag.select("span.bb_price");
		if (prices == null || prices.isEmpty())
			return;

		String price = prices.text();
		blank.setPriceStr(price);
	}

	/**
	 * This method attempts to populate a book's title from JSOUP document
	 * @param doc
	 * @param blank
	 */
	public static void fillBookTitle(Document doc, Book blank) {
		if (doc == null)
			return;


		Elements docMatches = doc.select("#btAsinTitle");
		if (docMatches == null || docMatches.isEmpty())
			return;

		Element titleTag = docMatches.first();
		if (titleTag == null || titleTag.childNodeSize() == 0)
			return;

		Node titleNode = titleTag.childNode(0);
		if (titleNode == null)
			return;

		String title = titleNode.toString();
		blank.setTitle(title);
	}
	
	/**
	 * This method attempts to populate a book's Authors, Editor, Forward, Intro from JSOUP document
	 * @param doc
	 * @param blank
	 */
	public static void fillBookContributers(Document doc, Book blank) {
		if (doc == null)
			return;
		
		Elements authorTags = doc.select("div.buying:has(span.byLinePipe)");
		if (authorTags ==null || authorTags.isEmpty())return;
				
		Element authorTag = authorTags.first();
		if (authorTag ==null )return;
		
		// Book contribute block
		Elements peeps = authorTag.select("a");
		Elements roles = authorTag.select("span.byLinePipe");		
		if (peeps ==null || peeps.isEmpty() || roles ==null||roles.isEmpty()) return;
		
		//I am assuming that there is a one to one relationship between these results.
		//If not, the results could be incorrect.
		
		int peepsSize = peeps.size();
		int rolesSize = roles.size();
		int minBound = (peepsSize < rolesSize ? peepsSize: rolesSize);
		for (int i=0;i<minBound;i++)
		{
			String person = null;
			Element p1 = peeps.get(i);
			if (p1 !=null&& p1.childNodeSize()>0)
				person = p1.childNode(0).toString();
			
			String role = null;
			Element r1 = roles.get(i);
			if (r1 !=null&& r1.childNodeSize()>0)
				role = r1.childNode(0).toString();
			
			if (role==null || person ==null) continue;
			
			switch (role)
			{
			case "(Author)":
				blank.addAuthor(person);
				break;
			case "(Editor)":
				blank.setEditor(person);
				break;
			case "(Foreword)":
				blank.setForewordBy(person);
				break;
			case "(Introduction)":
				blank.setIntroductionBy(person);
				break;
			default:
				System.out.println("Role value of "+role+" is not part of current model.");
				System.out.println("The value "+person+" would have been associated with this role.");
				break;
			}
			

		}

	}

	public static void fillBookDetails(Document doc, Book blank) {
		
	
		Elements productDetails = doc.select("h2:contains(Product Details) + div.content");
		if (productDetails ==null || productDetails.isEmpty())return;
		
		Element detailsTag = productDetails.first();
		if (detailsTag ==null )return;
		
		Elements productTags = detailsTag.select("div.content > ul ");
		if (productTags ==null || productTags.isEmpty())return;
		
		Element productTag = productTags.first();
		if (productTag ==null )return;
		
Elements pairs = productTag.select("li");
if (pairs ==null || pairs.isEmpty())return;

for (Element listItem : pairs) {
	// first Element is our key, once we know what it is we can
	// process the rest of the value
	if (listItem.children().isEmpty())
		continue;
	
	if (listItem.child(0) instanceof Element) {
		Element first = listItem.child(0);
		if (first.childNodeSize()==0) continue;
		
		String prop = first.childNode(0).toString();

		switch (prop) {
		case "Age Range:":
			String ageRange = listItem.childNode(1).toString();
			blank.setAgeRange(ageRange);
			break;
		case "Hardcover:":
		case "Paperback:":
			blank.setFormat(prop.substring(0, prop.lastIndexOf(':')));
			String pages = listItem.childNode(1).toString();
			blank.setPageNumStr(pages);
			break;
		case "Paperback":
			blank.setFormat(prop);
			break;
		case "Publisher:":
			String publisherStr = listItem.childNode(1).toString();
			//split on ; and ()
			String[] parts = publisherStr.split("\\(");
			String date = parts[1].substring(0, parts[1].lastIndexOf(')'));
			String[] parts2 = parts[0].split(";");
			String edition="";
			if (parts2.length>1)
			{
				edition = parts2[1];	
				blank.setEdition(edition);
			}
			String pub = parts2[0];
			//System.out.println(publisherStr+"::: "+ pub+"::: "+edition+"::: "+ date);
			blank.setPublisher(pub);
			blank.setReleaseDateStr(date);
			break;
		case "Language:":
			String lang = listItem.childNode(1).toString();
			blank.setLanguage(lang);
			break;
		case "ISBN-10:":
			String isbn10 = listItem.childNode(1).toString();
			blank.setIsbn_10(isbn10);
			break;
		case "ISBN-13:":
			String isbn13 = listItem.childNode(1).toString();
			blank.setIsbn_13(isbn13);
			break;
		case " Product Dimensions: ":
			String dim = listItem.childNode(1).toString();
			blank.setDimensionStr(dim);
			break;
		case "Shipping Weight:":
			String dims = listItem.childNode(1).toString();
			String[] massive = dims.split(" ");
			String value = massive[1];
			String metric = massive[2];
			try{
			blank.setWeightValue(Float.valueOf(value));
		}catch (NumberFormatException nfe){
			System.err.println("Error in converting shipping weight :"+value);
			nfe.printStackTrace();
		}	
			blank.setWeightUnitc(metric);
			break;
		default:
			break;
		}

		//System.out.println(prop);
		if (prop.equalsIgnoreCase("Shipping Weight:"))
			break;
	}
}
		
	}
}
