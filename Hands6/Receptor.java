package examples.agente3sbc;

import jade.core.Agent;

import javax.swing.plaf.multi.MultiFileChooserUI;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;


public class Receptor extends Agent {
    Environment clips;

    protected void setup() {
        addBehaviour(new TellBehaviour());
    }


    private class TellBehaviour extends Behaviour {
        boolean askDone = false;
        
        public void action() {
            clips = new Environment();
            ACLMessage mensaje = receive();
            String sintoma = "";


            if (mensaje != null) {
                System.out.println(getLocalName() + ": acaba de recibir un mensaje: ");
                System.out.println("\nPreparandose...");
                sintoma = mensaje.getContent();
                String[] sintomas = sintoma.split(",");
                System.out.println("Sintomas: ");
                for(int i = 0; i < sintomas.length; i++){
                    System.out.println("Sintoma ["+ (i+1) +"] "+sintomas[i]);
                }

                
                System.out.println("\nTu sintomas son: "+sintoma);
                try {
                    clips.load("/clips/java-src/agente3sbc/templates.clp");
                    clips.load("/clips/java-src/agente3sbc/rules.clp");
                    clips.reset();
                    for (int i = 0; i < sintomas.length; i++) {
                        clips.eval("(assert(sintoma "+sintomas[i]+"))");
                    }
                    clips.run();

                    //Sacar hecho
                    String evaluador = "(find-all-facts ((?m diagnostico)) TRUE)";

                    FactAddressValue fv = (FactAddressValue) ((MultifieldValue) clips.eval(evaluador)).get(0);
                    String msj = fv.getSlotValue("enfermedad").toString(); 


                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    AID id = new AID();
                    id.setLocalName("e1");
                    msg.setContent(msj);
                    msg.addReceiver(id);
                    send(msg);
                    //System.out.println("Tu diagnostico es: " + msj);
                    askDone = true;
                } catch (CLIPSException e) { 
                    System.out.println(e);
                }
            } 
        } 

        public boolean done() {
            if (askDone)
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