(ns formatron.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [formatron.core-test]))

(doo-tests 'formatron.core-test)
