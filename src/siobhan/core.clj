(ns siobhan.core)
;; require clj-schema

;; sample schema map for debugging
(def schema-map {:tablename "test" :columns ["id", "name", "date_created", "last_updated"]})

(defn get-getters 
  "Write a series of php get() methods for the given columns"
  [columns]
  (str
    (for [col columns]
      (str 
        "get"
        (.toUpper col)
        "() { return $this->"
        col
        "; }"
      )
    )
  )
)

(defn get-setters 
  "Write a series of set() methods for the given columns"
  [columns]
  (str
    (for [col columns]
      (str
        "set"
        (.toUpper col)
        "($val) { $this->"
        col
        " = $val; return $this; }"
      )
    )
  )
)

(defn get-table-model
  "Write a string for the model class for the given db table"
  [table-map]
  (str 
    "<?php class " 
    (:tablename table-map) 
    " { " 
    (get-getters (:columns table-map)) 
    " " 
    (get-setters (:columns table-map)) 
    " }"
  )
)

(defn get-table-mapper
  "Write a string for the mapper class for the given db table"
  [table-map]
  (str 
    "<?php class "
    (:tablename table-map)
    "Mapper { "
    (get-select-function table-map)
    " "
    (get-fetch-functions table-map)
    " }"    
  )
)
