

;; Defining a rule for finding customer names and printing such names
(defrule my-rule11
   (customer (name ?n)) => (printout t "Customer name found:" ?n  crlf ))


;;Defining a rule for finding "electronic products"
(defrule my-rule13
   (product (category electronics) (name ?name))
   =>
   (printout t "Electronic product found: " ?name crlf))


;; Defining a rule for finding smartphones cheaper than 50 dlls
;; note the use of NOT, if Jess does not find a match for a given pattern then the rule returns a FALSE
;; however you can use NOT for making the rule to return true

;;(defrule my-rule14
   ;;(not (product (category smartphone) {price<50} (name ?n)))
  ;; =>
  ;; (printout t "no smartphones cheaper than 50" crlf ))


;; Defining a rule for finding smartphones cheaper than 100 dlls
;;(defrule my-rule15
   ;;(product (category smartphone) {price<100} (name ?n))
  ;; =>
  ;; (printout t ?n " is cheaper than 100 dlls" crlf ))



