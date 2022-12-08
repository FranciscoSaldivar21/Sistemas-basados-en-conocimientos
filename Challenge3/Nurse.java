package examples.agente4sbc;

import jade.core.Agent;
import jade.core.*;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;

public class Nurse extends Agent {

    protected void setup() {
        addBehaviour(new AskBehaviour());
        addBehaviour(new Request());
    }

    private class AskBehaviour extends SimpleBehaviour {
        private String fact = "ready";
        boolean tellDone = false;

        public void action() {
            System.out.println(getLocalName() + " is ready");
            System.out.println(getLocalName() + ": Looking for Chief Surgeon Agent");
            AID id = new AID();

            //Surgeon Agent
            id.setLocalName("chief");

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(id);
            msg.setSender(getAID());
            msg.setContent(fact);
            msg.setLanguage("English");
            send(msg);

            tellDone = true;
        }

        public boolean done() {
            if (tellDone)
                return true;
            else
                return false;
        }
    } // END of inner class ...Behaviour

    private class Request extends CyclicBehaviour{
        public void action(){
            ACLMessage msg = receive();
            if (msg != null){
                System.out.println("Chief says: "+msg.getContent());
            }
        }
    }

}
