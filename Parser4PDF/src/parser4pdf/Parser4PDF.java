package parser4pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

public class Parser4PDF {	
    PDFParser p;
    String p2text;
    PDFTextStripper pdfStripper;
    PDDocument pdDoc;
    COSDocument cosDoc;
    PDDocumentInformation pdDocInfo;
   
    String pdftoText(String fileName) {
       System.out.println("Reading text from PDF file " + fileName + "....");
        File f = new File(fileName);
        try {
            p = new PDFParser(new FileInputStream(f));
        } catch (Exception e) {
            System.out.println("Cannot to open PDF Parser.");
            return null;
        }
  
        try {
            p.parse();
            cosDoc = p.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            p2text = pdfStripper.getText(pdDoc);
        } catch (Exception e) {
            System.out.println("Exception occured while parsing the PDF Document.");
            e.printStackTrace();
            try {
                   if (cosDoc != null) cosDoc.close();
                   if (pdDoc != null) pdDoc.close();
               } catch (Exception e1) {
               e.printStackTrace();
            }
            return null;
        }
        System.out.println("Done.");
        return p2text;
    }

    // Write the parsed text from PDF to a file
    void writeTexttoFile(String pdf2text, String fileName)throws Exception{
   	 System.out.println("Writing PDF to text file " + fileName + "..");
   	 PrintWriter out = new PrintWriter(fileName);
   	 out.print(pdf2text);
   	 out.close();  
   	 System.out.println("Done.");
    }

public static void main(String args[]) throws Exception {
        Parser4PDF pdfObj = new Parser4PDF();
        String pdfToText = pdfObj.pdftoText("camera-ready.pdf");
        System.out.println("The text parsed from the PDF file.." + pdfToText);
        pdfObj.writeTexttoFile(pdfToText, "PDFFile2TXT.txt");
}
}
