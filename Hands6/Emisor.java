package examples.agente3sbc;

import jade.core.Agent;
import jade.core.*;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;

public class Emisor extends Agent {

    protected void setup() {
        addBehaviour(new AskBehaviour());
        addBehaviour(new Request());
    }

    private class AskBehaviour extends Behaviour {
        private String sintoma = "noSabor,noOlfato,diarrea";
        boolean tellDone = false;

        public void action() {
            System.out.println("Invoking Tell");
            System.out.println(getLocalName() + ": Preparandose para enviar sintoma al receptor");
            AID id = new AID();
            id.setLocalName("r1");

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(id);
            msg.setSender(getAID());
            msg.setContent(sintoma);
            msg.setLanguage("English");
            send(msg);

            System.out.println(getLocalName() + ": ... What are you up to");
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
                System.out.println("El diagnostico recibido fue: "+msg.getContent());
            }else{
                block();
            }
        }
    }

}
