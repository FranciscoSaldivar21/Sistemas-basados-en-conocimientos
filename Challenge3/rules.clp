
(defrule anesteciar 
    (patient (status ready)) => (retract 1)(assert (patient(status anesteciar)))
)


(defrule anesteciado 
    (patient (status anesteciar))(anesteciar) => (retract *)(assert (patient(status anesteciado)))
)


(defrule start-surgery 
    (patient (status anesteciado))(start) => (retract *)(assert (surgery(status operado)))
)