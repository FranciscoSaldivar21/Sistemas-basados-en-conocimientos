package examples.agente4sbc;

import javax.swing.plaf.multi.MultiFileChooserUI;
import jade.core.Agent;
import jade.core.*;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;

public class Surg extends Agent {
    Environment clips;

    protected void setup() {
        addBehaviour(new TellBehaviour());
    }

    private class TellBehaviour extends SimpleBehaviour {
        boolean tellDone = false;

        public void action() {
            clips = new Environment();
            ACLMessage mensaje = receive();
            String status = "";
            System.out.println(getLocalName() + " is ready");

            if (mensaje != null) {
                status = mensaje.getContent();
                System.out.println(getLocalName() + ": acaba de recibir un mensaje. Estado paciente: " + status);

                try {
                    clips.clear();
                    clips.load("/clips/java-src/agente4sbc/templates.clp");
                    clips.load("/clips/java-src/agente4sbc/rules.clp");
                    clips.reset();
                    clips.eval("(assert(patient (status " + status + ")))");
                    clips.eval("(assert(start))");
                    clips.run();

                    // Sacar hecho
                    String evaluador = "(find-all-facts ((?m surgery)) TRUE)";

                    FactAddressValue fv = (FactAddressValue) ((MultifieldValue) clips.eval(evaluador)).get(0);
                    String patient = fv.getSlotValue("status").toString();

                    System.out.println(patient);

                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    AID id = new AID();
                    id.setLocalName("chief");
                    msg.setContent(patient);
                    msg.addReceiver(id);
                    send(msg);

                    tellDone = true;

                } catch (CLIPSException e) {
                    System.out.println(e);
                }
            } else {
                block();
            }
        }

        
         public boolean done() {
            if (tellDone)
                return true;
            else
                return false;
         }
         

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}
