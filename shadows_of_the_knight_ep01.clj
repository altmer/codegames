(ns Player
  (:gen-class))

(defn up [coords search-area location]
  (assoc coords :y0 (:y0 search-area) :y1 (dec (:y location))))
(defn down [coords search-area location]
  (assoc coords :y0 (inc (:y location)) :y1 (:y1 search-area)))
(defn right [coords search-area location]
  (assoc coords :x0 (inc (:x location)) :x1 (:x1 search-area)))
(defn left [coords search-area location]
  (assoc coords :x0 (:x0 search-area) :x1 (dec (:x location))))
(defn level-x [coords search-area location]
  (assoc coords :x0 (:x location) :x1 (:x location)))
(defn level-y [coords search-area location]
  (assoc coords :y0 (:y location) :y1 (:y location)))

(defn new-search-area
  [search-area location bomb-direction]
  (case bomb-direction
    "U"  (-> {} (level-x search-area location) (up search-area location))
    "UR" (-> {} (right search-area location) (up search-area location))
    "R"  (-> {} (right search-area location) (level-y search-area location))
    "DR" (-> {} (right search-area location) (down search-area location))
    "D"  (-> {} (level-x search-area location) (down search-area location))
    "DL" (-> {} (left search-area location) (down search-area location))
    "L"  (-> {} (left search-area location) (level-y search-area location))
    "UL" (-> {} (left search-area location) (up search-area location))))

(defn middle-coord
  [a b]
  (quot (+ a b) 2))

(defn jump
  [search-area]
  {:x (middle-coord (:x0 search-area) (:x1 search-area))
   :y (middle-coord (:y0 search-area) (:y1 search-area))})

(defn -main [& args]
  (let [W (read) H (read) N (read) X0 (read) Y0 (read)]
    ; W: width of the building.
    ; H: height of the building.
    ; N: maximum number of turns before game over.
    (def search-area {:x0 0 :y0 0 :x1 W :y1 H})
    (def location {:x X0 :y Y0})
    (while true
      (let [bombDir (str (read))]
        ; bombDir: the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)
        ; the location of the next window Batman should jump to.
        (def search-area (new-search-area search-area location bombDir))
        (def location (jump search-area))
        (println (str (:x location) " " (:y location)))))))
