package clips;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.clipsrules.jni.*;
public class Clips {

    private static Environment clips;

    public static void main(String[] args) throws CLIPSException {
        clips = new Environment();
        try {
            //Persons and partership
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/load-persons.clp");
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/load-persons-rules.clp");
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/facts.clp");
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/facts.clp");
            clips.eval("(rules)");
            clips.eval("(facts)");
            clips.run(); 
            
            //Market
            /*
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/templates.clp");
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/rules.clp");
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/facts.clp");
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/facts.clp");
            clips.eval("(rules)");
            clips.eval("(facts)");
            clips.run();  */
            
            //Products
            /*
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/products/products-templates.clp");
            clips.reset();
            clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/products/products-rules.clp");
            clips.reset();
            clips.eval("(rules)");
            clips.eval("(facts)");
            clips.run(); */
        } catch (CLIPSLoadException ex) {
            Logger.getLogger(Clips.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
