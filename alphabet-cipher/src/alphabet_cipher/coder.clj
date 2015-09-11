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

(defn match-keyword-length-to-message [secret string]
  (let [partsubs (subs secret 0 (mod (count string) (count secret)))
        repeated (apply str (repeat (quot (count string) (count secret)) secret))]
    (apply str repeated partsubs)))

(defn keyword-message->pairs
  [secret string]
  (map #(conj [] %1 %2)
       (seq (clojure.string/upper-case string))
       (seq (clojure.string/upper-case secret))))

(defn encode [keyword message]
  (let [repeating-secret (match-keyword-length-to-message keyword message)
        letter-pairs (keyword-message->pairs repeating-secret message)]
    (clojure.string/lower-case (apply str (map #(cipher-letter %) letter-pairs)))))

(defn find-row [chr]
  (let [row-number (alpha-pos chr alpha)]
       (rotate row-number)))

(defn decode-pair
  "keyword char and message char unlock decoded char"
  [[keyword-char message-char]]
  (let [rotated-row (find-row keyword-char)
        decode-pos (alpha-pos message-char rotated-row)]
    (subs alpha decode-pos (+ 1 decode-pos))))

(defn decode [keyword message]
  (let [keyword-seq (match-keyword-length-to-message keyword message)
        pairs (map #(conj [] %1 %2)
                (seq (clojure.string/upper-case keyword-seq))
                (seq (clojure.string/upper-case message)))]
    (clojure.string/lower-case (apply str (map #(decode-pair %) pairs)))))
