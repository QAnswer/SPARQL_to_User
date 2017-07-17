package eu.wdaqua.SPARQLtoUser.knowledgebase;

import org.apache.jena.query.*;

import java.util.ArrayList;

/**
 * Created by dryous on 10/07/2017.
 */
public class musicbrainz extends KnowledgeBase  {

    public musicbrainz(String endpoint){

    }

    @Override
    public ArrayList<String> getLabel(String uri, String language, String kb, String ep) {
        ArrayList<String> labels = new ArrayList<>();
        String res

                = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "+
                "SELECT ?label ?image ?coordinates ?wikilink where { " +
                "  OPTIONAL{ " +
                //"<" + value + "> rdfs:label ?label . FILTER (lang(?label)=\""+ lang +"\" || lang(?label)=\"en\" || lang(?label)=\"de\" || lang(?label)=\"fr\" || lang(?label)=\"it\")" +
                "    <" + uri + "> foaf:name ?label . " +
                "  } " +
                "  OPTIONAL{ " +
                "    <" + uri + ">  <http://purl.org/dc/elements/1.1/title> ?label . " +
                "  } " +
                "  OPTIONAL{ " +
                "    <" + uri + ">  <http://www.w3.org/2000/01/rdf-schema#label> ?label . " +
                "  } " +
                "  OPTIONAL{ " +
                "    <" + uri + ">  foaf:isPrimaryTopicOf ?wikilink . " +
                "  } " +
                "} ";

        System.out.println("music brainz sparql Label : "+res);
        Query query1 = QueryFactory.create(res);
        QueryExecution qExe = QueryExecutionFactory.sparqlService(ep, query1);
        ResultSet result = qExe.execSelect();
        while (result.hasNext()) {
            QuerySolution rsnext = result.next();
            labels.add(rsnext.getLiteral("label").getLexicalForm().toString());
            System.out.println(rsnext.getLiteral("label").getLexicalForm().toString());
            if (rsnext.getLiteral("x") != null) {
                labels.add(rsnext.getLiteral("wikilink").getLexicalForm().toString());
            }
        }
        return labels;
    }



    @Override
    public ArrayList<String> getAlternative(String res, String predicate, String language, String kb, String ep) {
        ArrayList<String> altern = new ArrayList<>();
        Query query1 = QueryFactory.create(res);
        QueryExecution qExe = QueryExecutionFactory.sparqlService(ep, query1);
        ResultSet result;
        result = qExe.execSelect();
        while (result.hasNext()) {
            QuerySolution rsnext = result.next();//
            String s = predicate.replace("?","");
            String nxt = rsnext.getResource(s).toString();
            altern.add(nxt);
        }
        return altern;    }


}