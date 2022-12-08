package examples.agente2sbc;

import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
//import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;

public class Emisor extends Agent {

    Environment clips;

    protected void setup() {
        System.out.println("AGENTE " + getLocalName() + " INICIADO");

        clips = new Environment();
        addBehaviour(new TellBehaviour());
        addBehaviour(new AskBehaviour());

    }

    private class TellBehaviour extends Behaviour {

        boolean isTellBehaviourDone = false;

        public void action() {

            try {

                clips.eval("(clear)");
                clips.reset();

                // MARKET 
                /* 
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/templates.clp");
                clips.reset();
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/rules.clp");
                clips.reset();
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/facts.clp");
                clips.reset();
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/market/facts.clp"); */

                // PERSONS
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/load-persons.clp");
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/load-persons-rules.clp");
                clips.reset();
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/facts.clp");
                clips.reset();
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/persons/facts.clp");

                // PRODUCTS
                /* 
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/products/products-templates.clp");
                clips.reset();
                clips.load("/Users/Francisco Saldivar/OneDrive/Documentos/clips/products/products-rules.clp");
                clips.reset(); */

            } catch (Exception e) {
            }

            isTellBehaviourDone = true;
        }

        public boolean done() {
            if (isTellBehaviourDone)
                return true;
            return false;
        }

    }

    private class AskBehaviour extends Behaviour {

        boolean isAskBehaviourDone = false;

        public void action() {

            try {
                clips.eval("(facts)");
                clips.eval("(rules)");
                clips.run();

            } catch (Exception e) {
            }

            isAskBehaviourDone = true;
        }

        public boolean done() {
            if (isAskBehaviourDone)
                return true;
            return false;
        }

        public int onEnd() {
            System.out.println("AGENTE " + getLocalName() + " TERMINADO");
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}
