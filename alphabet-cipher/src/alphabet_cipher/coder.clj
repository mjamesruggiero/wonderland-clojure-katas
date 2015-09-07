(ns alphabet-cipher.coder)

(def alpha "ABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn rotate [n]
  (str (subs alpha n (count alpha)) (subs alpha 0 n)))

(defn alpha-pos
  "position of a letter in a seq" [letter seqnce]
  (.indexOf seqnce letter))

(defn row-column [row-letter col-letter]
  [(alpha-pos row-letter alpha) (alpha-pos col-letter alpha)])

(defn cipher-letter [row-letter col-letter]
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
  (map str
       (seq (clojure.string/upper-case string))
       (seq (clojure.string/upper-case secret))))

(defn encode [keyword message]
  "encodeme")

(defn decode [keyword message]
  "decodeme")
