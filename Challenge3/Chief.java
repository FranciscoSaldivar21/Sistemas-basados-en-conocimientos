package examples.agente4sbc;

import jade.core.Agent;

import javax.swing.plaf.multi.MultiFileChooserUI;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;


public class Chief extends Agent {
    Environment clips;

    protected void setup() {
        addBehaviour(new TellBehaviour());
    }


    private class TellBehaviour extends CyclicBehaviour {
        
        public void action() {
            clips = new Environment();
            ACLMessage msg = receive();
            String status = "";
            System.out.println(getLocalName() + " is ready");


            if (msg != null) {
                status = msg.getContent();
                System.out.println(getLocalName() + ": acaba de recibir un mensaje. Estado paciente: "+ status);

                try {
                    clips.clear();
                    clips.reset();
                    clips.load("/clips/java-src/agente4sbc/templates.clp");
                    clips.load("/clips/java-src/agente4sbc/rules.clp");
                    clips.eval("(assert(patient (status "+status+")))");
                    clips.run();

                    //Sacar hecho
                    String evaluador = "(find-all-facts ((?m patient)) TRUE)";

                    FactAddressValue fv = (FactAddressValue) ((MultifieldValue) clips.eval(evaluador)).get(0);
                    String patient = fv.getSlotValue("status").toString(); 

                    ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
                    AID id = new AID();
                    switch (patient) {
                        case "anesteciar":
                            //Enviar al anestesiologo
                            System.out.println(patient);
                            id.setLocalName("anest");
                            break;
                        case "anesteciado":
                            //Poner codigo aquí para enviar a nurse para operar
                            id.setLocalName("surg");
                            break;
                        case "operado":
                            System.out.println("El paciente fue operado");
                            System.out.println("\n\n---------------------------------\n\n");
                            break;
                        default:
                            break;
                    }

                    msg.setSender(getAID());
                    mensaje.setContent(patient);
                    mensaje.addReceiver(id);
                    send(mensaje);

                } catch (CLIPSException e) { 
                    System.out.println(e);
                }
            }else{
                block();
            }
        } 

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}