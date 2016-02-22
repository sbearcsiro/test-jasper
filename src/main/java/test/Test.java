package test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

//        String rtfText = Files.toString(new File("reports/rtf.txt"), Charsets.UTF_8);
//        String htmlText = Files.toString(new File("reports/html.txt"), Charsets.UTF_8);

        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.put("RtfText", rtfText);
//        parameters.put("HtmlText", htmlText);
        Map<String, Object> options = new LinkedHashMap<>();
//        "profileId": "Acaciella",
//                "opusId": "e2e16824-72d7-4d83-a264-fa041295b3bf",
//                "attributes": "true",
//                "map": null,
//                "nomenclature": "true",
//                "taxonomy": "true",
//                "bibliography": null,
//                "links": null,
//                "bhllinks": null,
//                "specimens": null,
//                "conservation": null,
//                "status": null,
//                "images": null,
//                "children": null,
//                "email": null,
//                "allowFineGrainedAttribution": true,
//                "displayToc": false
        options.put("profileId", "Acaciella");
        options.put("opusId", "e2e16824-72d7-4d83-a264-fa041295b3bf");
        options.put("attributes", true);
        options.put("nomenclature", "true");
        options.put("taxonomy", "true");
        options.put("allowFineGrainedAttribution", true);
        options.put("displayToc", false);

        parameters.put("PROFILES_REPORT_OPTIONS", options);
        //parameters.put("REPORT_FILE_RESOLVER", new SimpleFileResolver(new File("")));
        parameters.put("QR_CODE", new ByteArrayInputStream(QRCode.from("http://google.com").withSize(150, 150).to(ImageType.JPG).stream().toByteArray()));

//
//        DefaultJasperReportsContext instance = DefaultJasperReportsContext.getInstance();
//        instance.
//        new JasperCompileManager();

        System.out.println(new File(".").getAbsolutePath());
//
//        DefaultJasperReportsContext instance = DefaultJasperReportsContext.getInstance();
//        DefaultRepositoryService defaultRepositoryService = new DefaultRepositoryService(instance);
//        defaultRepositoryService.setFileResolver(new SimpleFileResolver(new File("src/main/jasperreports/")));
        //RepositoryUtil.
        //instance.setProperty(FileRepositoryServiceExtensionsRegistryFactory.PROPERTY_FILE_REPOSITORY_ROOT,);


        JasperReport jasperReport = JasperCompileManager.compileReport("PROFILES.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_backcover.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_colophon.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_cover.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_acknowledgements.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_attributes.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_bhl.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_bibliography.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_conservation.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_images.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_links.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_map.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_nomenclature.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_specimens.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_status.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_detail_taxonomy.jrxml");
        JasperCompileManager.compileReportToFile("PROFILES_toc.jrxml");

        System.out.println(Resources.toString(Resources.getResource("data.json"), Charsets.UTF_8));

        JRDataSource ds = new JsonDataSource(Resources.getResource("data.json").openStream());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        System.err.println("Filling time : " + (System.currentTimeMillis() - start));

        //start = System.currentTimeMillis();
        //boolean b = JasperPrintManager.printReport(jasperPrint, true);
        //System.err.println("Printing time : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        JasperExportManager.exportReportToPdfFile(jasperPrint, "test.pdf");
        System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
    }


}
