package examples.agente1sbc;

import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;


public class Receptor extends Agent {
    Environment clips;

    protected void setup() {
        addBehaviour(new TellBehaviour());
    }


    private class TellBehaviour extends SimpleBehaviour {
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
                askDone = true;
                try {
                    clips.reset();
                    for (int i = 0; i < sintomas.length; i++) {
                        clips.eval("(assert(sintoma "+sintomas[i]+"))");
                    }
                    clips.load("/clips/java-src/agante1sbc/rules.clp");
                    System.out.println();
                    clips.run(); 
                } catch (CLIPSException e) { 
                    System.out.println(e);
                }
            } 
            // clips.run();


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