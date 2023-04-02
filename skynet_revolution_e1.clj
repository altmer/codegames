(ns Player
  (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn in?
  [coll item]
  (some #(= item %) coll))

(defn is-gateway?
  "Returns whether given node is a gateway"
  [n gateways-list]
  (in? gateways-list n))

(defn links
  "Returns list of links for specified node"
  [graph n]
  (get graph n `()))

(defn add-link
  "Adds one direction link from n1 to n2 in given graph"
  [graph n1 n2]
  (assoc
    graph
    n1
    (conj
      (links graph n1)
      n2)))

(defn remove-link
  "Removes one directional link from n1 to n2"
  [graph n1 n2]
  (assoc
    graph
    n1
    (remove
      #(= n2 %)
      (links graph n1))))

(defn unlink-nodes
  "Unlinks 2 nodes in given graph"
  [graph n1 n2]
  (->
    graph
    (remove-link n1 n2)
    (remove-link n2 n1)))

(defn link-nodes
  "Links 2 nodes in given graph"
  [graph n1 n2]
  (->
    graph
    (add-link n1 n2)
    (add-link n2 n1)))

(defn populate-queue
  "Populates queue with all nodes that are accessible from given node and not visited yet"
  [queue visited-nodes graph node]
  (reduce
    #(if (and (not (contains? visited-nodes %2)) (not (in? queue %2)))
       (conj %1 {:node %2 :parent node})
       %1)
    queue
    (links graph node)))

(defn find-closest-gateway
  "Finds path from given node to the closest gateway. Returns pair of values: last segment of the path."
  [graph node gateways-list]
  (loop [queue [{:node node :parent nil}]
         visited-nodes (set [node])]
    (let [[current-position & rest-queue-list] queue
          current-node (:node current-position)
          rest-queue (into [] rest-queue-list)]
      (if (is-gateway? current-node gateways-list)
        [current-node (:parent current-position)]
        (recur
          (populate-queue rest-queue visited-nodes graph current-node)
          (conj visited-nodes current-node))))))

; recur (conj rest-queue ,,,)
(defn -main [& args]
  (let [N (read) L (read) E (read)]
    (def gateways [])
    (def networkMap {})
    ; N: the total number of nodes in the level, including the gateways
    ; L: the number of links
    ; E: the number of exit gateways
    (loop [i L]
      (when (> i 0)
        (let [N1 (read) N2 (read)]
          ; N1: N1 and N2 defines a link between these nodes
         (def networkMap (link-nodes networkMap N1 N2))
         (recur (dec i)))))
    (loop [i E]
      (when (> i 0)
        (let [EI (read)]
          ; EI: the index of a gateway node
         (def gateways (conj gateways EI))
         (recur (dec i)))))
    (while true
      (let [SI (read)]
        ; SI: The index of the node on which the Skynet agent is positioned this turn

        ; (binding [*out* *err*]
        ;   (println (find-closest-gateway networkMap SI gateways)))
        (let [[n1 n2] (find-closest-gateway networkMap SI gateways)]
          (println n2 n1)
          (def networkMap (unlink-nodes networkMap n1 n2)))))))
        ; Example: 0 1 are the indices of the nodes you wish to sever the link between
