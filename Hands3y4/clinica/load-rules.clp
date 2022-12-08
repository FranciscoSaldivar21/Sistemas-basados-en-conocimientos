;; Defining a rule for finding persons names and printing such names

(defrule my-rule1 (patient (status waiting)) => (assert (patient(status ready))))
