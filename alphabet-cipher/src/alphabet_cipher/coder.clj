(ns alphabet-cipher.coder)

(def alpha "ABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn rotate [n]
  (str (subs alpha n (count alpha)) (subs alpha 0 n)))

(defn alpha-pos
  "position of a letter in a seq" [letter seqnce]
  (.indexOf seqnce (int letter)))

(defn row-column [row-letter col-letter]
  [(alpha-pos row-letter alpha) (alpha-pos col-letter alpha)])

(defn cipher-letter [[row-letter col-letter]]
  (let [rc-pair (row-column row-letter col-letter)
        row (first rc-pair)
        column (second rc-pair)
        rotated (subs (rotate row) column (+ column 1))]
   rotated))

(defn match-secret-to-string [secret string]
  (let [partsubs (subs secret 0 (mod (count string) (count secret)))
        repeated (apply str (repeat (quot (count string) (count secret)) secret))]
    (apply str repeated partsubs)))

(defn map-secret-and-string
  [secret string]
  (map #(conj [] %1 %2)
       (seq (clojure.string/upper-case string))
       (seq (clojure.string/upper-case secret))))

(def rs
  (match-secret-to-string "vietnam" "smellslikevictory"))

(map-secret-and-string rs "smellslikevictory")

(defn encode [keyword message]
  (let [repeating-secret (match-secret-to-string keyword message)
        letter-pairs (map-secret-and-string repeating-secret message)]
    (clojure.string/lower-case (apply str (map #(cipher-letter %) letter-pairs)))))

;(encode "vietnam" "smellslikevictory")

;(encode "vigilance" "meetmeontuesdayeveningatseven")
;(encode "vietnam" "smellslikevictory")

(defn decode [keyword message]
  "decodeme")
