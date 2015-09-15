(ns alphabet-cipher.coder)

(def alpha "abcdefghijklmnopqrstuvwxyz")

(defn rotate [n]
  (apply str (concat (drop n alpha) (take n alpha))))

(defn alpha-pos
  "position of a letter in a seq"
  [letter seqnce]
  (.indexOf seqnce (int letter)))

(defn row-column [row-char col-char]
  [(alpha-pos row-char alpha) (alpha-pos col-char alpha)])

(defn cipher-letter [[row-char col-char]]
  (let [[row column] (row-column row-char col-char)]
        (subs (rotate row) column (+ column 1))))

(defn match-keyword-length-to-message [kw message]
    (apply str (take (count message) (apply concat (repeat kw)))))

(defn keyword-message->pairs
  [secret string]
  (map vector (seq string) (seq secret)))

(defn find-row [chr]
  (let [row-number (alpha-pos chr alpha)]
       (rotate row-number)))

(defn decode-pair
  "keyword char and message char unlock decoded char"
  [[keyword-char message-char]]
  (let [rotated-row (find-row keyword-char)
        decode-pos (alpha-pos message-char rotated-row)]
    (subs alpha decode-pos (+ decode-pos 1))))

(defn encode [keyword message]
  (let [lengthened-kw (match-keyword-length-to-message keyword message)
        letter-pairs (keyword-message->pairs lengthened-kw message)]
    (apply str (map #(cipher-letter %) letter-pairs))))

(defn decode [keyword message]
  (let [keyword-seq (match-keyword-length-to-message keyword message)
        pairs (keyword-message->pairs message keyword-seq)]
    (apply str (map #(decode-pair %) pairs))))
