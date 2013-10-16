package control;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import products.Book;
import products.Box;
import products.Item;
import search.AmazonToBookMapper;
import algorithm.HeavyFirstFit;

/**
 * This project takes a source directory containing HTMLfile that represent book listing on Amazon.
 * We extract interesting details about the books.  THen pack the books into boxes with the restriction
 * that the boxes can only hold 10 pounds.  Afterwards we export the result to a JSON document.
 */
public class Packer {

	Path sourceDir;
	Path ouputDir;
	
	public Packer(String source, String output) {
		sourceDir = Paths.get(source);
		 ouputDir = Paths.get(output);
	}
	
	private void start() {
		List<Item> items = new ArrayList<Item>();
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir,"*.html")) {
			for (Path entry : stream) 
			{			
				parseResource(items, entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HeavyFirstFit alg = new HeavyFirstFit();
		List<Box> boxes = alg.packItems(items);

		Exporter exp = new Exporter();
		exp.outputBoxesToJSON(ouputDir.toString()+File.separator+"out.json",boxes);

	}

	private void parseResource(List<Item> items, Path entry) {
		try {
			Document doc = Jsoup.parse(entry.toFile(), "UTF-8", "http://www.amazon.com/");

			Book worm = new Book();
			AmazonToBookMapper.fillBookTitle(doc, worm);
			AmazonToBookMapper.fillBookContributers(doc, worm);
			AmazonToBookMapper.fillBookPrice(doc, worm);
			AmazonToBookMapper.fillBookDetails(doc, worm);
			items.add(worm);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The expected arguments are 
	 * 1. Source directory containing HTML files
	 * 2. Output directory
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
	        System.out.println("Please pass in the path to the data source folder and path for the results");
	        return;
	    } 
		String source = args[0];
		String output = args[1];
		
		File sourceF = new File(source);
		if (!sourceF.exists())
		{
			System.out.println("Source folder does not exist.");
	        return;
		}
		else if (!sourceF.isDirectory())
		{
			System.out.println("Source is not a folder.");
	        return;
		}
		File outputF = new File(output);
		if (!outputF.exists())
		{
			System.out.println("OutPut folder does not exist.");
	        return;
		}
		else if (!outputF.isDirectory())
		{
			System.out.println("Ouput path is not a folder.");
	        return;
		}
		Packer pack = new Packer(source, output);
		pack.start();
	}

	

}
