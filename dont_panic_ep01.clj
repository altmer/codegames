(ns Player
  (:gen-class))

(def elevators {})
(def solved-floors #{})

(defn target [exitFloor exitPos currentFloor]
  (if (= currentFloor exitFloor)
    exitPos
    (get elevators currentFloor)))

(defn change-direction? [current-pos target-pos direction]
  (or
    (and (= direction "LEFT") (> target-pos current-pos))
    (and (= direction "RIGHT") (< target-pos current-pos))))

(defn -main [& args]
  (let [nbFloors (read) width (read) nbRounds (read) exitFloor (read)
        exitPos (read) nbTotalClones (read) nbAdditionalElevators (read)
        nbElevators (read)]
    (loop [i nbElevators]
      (when (> i 0)
        (let [elevatorFloor (read) elevatorPos (read)]
          (def elevators (assoc elevators elevatorFloor elevatorPos))
          (recur (dec i)))))
    (while true
      (let [cloneFloor (read) clonePos (read) direction (str (read))]
        ; (binding [*out* *err*]
        ;   (println "Debug messages..."))
        (if (contains? solved-floors cloneFloor)
          (println "WAIT")
          (let [targetPos (target exitFloor exitPos cloneFloor)]
            (if (change-direction? clonePos targetPos direction)
              (do
                (def solved-floors (conj solved-floors cloneFloor))
                (println "BLOCK"))
              (println "WAIT"))))))))
