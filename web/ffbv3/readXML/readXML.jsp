<%@page import="javax.servlet.jsp.JspWriter"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="java.io.File"%>
<%@page import="com.fz.ffbv3.service.blocks.blocksDAO" %>

<%!
  public void baca(JspWriter out, String nmFile) {

    try {
        out.println("<p/>nmFile : "+nmFile+"<p/>");
	File fXmlFile = new File(nmFile);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	out.println("Root element :" + doc.getDocumentElement().getNodeName()+"<br/>\n");

	NodeList nList = doc.getElementsByTagName("Placemark");

	out.println("----------------------------<br/>\n");

        String desc, poly;
        String[] descs;
        String[] polys;
        String[] coords;
        String divID, blockID;
        double xy,x1,y1,x2,y2;
        int j;
        String vals;

out.println("blockID, divID, x1, y1, x2, y2<br/>\n");
	for (int temp = 0; temp < nList.getLength(); temp++) {
                vals = "";
		Node nNode = nList.item(temp);

		//out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;
                        desc = eElement.getElementsByTagName("description").item(0).getTextContent();
                        poly = eElement.getElementsByTagName("coordinates").item(0).getTextContent();
                        descs = desc.split(", ");
                        polys = poly.split("\n");
                        blockID = descs[4];
                        divID = descs[1] + descs[2].replace("DIVISI ","");
                        
                        if (blockID.compareTo("NULL")<0) {
                            //out.println("name : " + eElement.getElementsByTagName("name")+"<br/>\n");
                            out.println(blockID + ", ");
                            out.println(divID + ", ");
                            x1 = 0; 
                            x2 = 0;
                            y1 = 0;
                            y2 = 0;
                            j = 0;
                            for (int i=0; i<polys.length ;i++) {
                                coords = polys[i].split(",");
                                if (coords.length>=2) {
                                    if ( j==0 ) {
                                        x1 = Double.valueOf(coords[0]);
                                        y1 = Double.valueOf(coords[1]);
                                        x2 = x1;
                                        y2 = y1;
                                    } else { 
                                        xy = Double.valueOf(coords[0]);
                                        if (xy < x1) x1 = xy;
                                        if (xy > x2) x2 = xy;
                                        xy = Double.valueOf(coords[1]);
                                        if (xy < y1) y1 = xy;
                                        if (xy > y2) y2 = xy;
                                    }
                                    j++;
                                }
                            }
                            out.println(String.valueOf(x1) + ", ");
                            out.println(String.valueOf(y1) + ", ");
                            out.println(String.valueOf(x2) + ", ");
                            out.println(String.valueOf(y2) +  "<br/>\n");

//                        String result = (blocksDAO.save(blockID, divID, x1, y1, x2, y2))?"Succeed":"Failed";
//                        out.println("Saved : " + result + "<br/>\n");
                    }
		} else 
                out.println("ZONK...");
	}

    } catch (Exception e) {
	e.printStackTrace();
    }
  }

%>