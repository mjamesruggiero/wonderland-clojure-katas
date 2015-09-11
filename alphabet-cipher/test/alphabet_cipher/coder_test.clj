(ns alphabet-cipher.coder-test
  (:require [clojure.test :refer :all]
            [alphabet-cipher.coder :refer :all]))

(deftest test-rotate
  (testing "can rotate the alpha sequence"
    (is (= "bcdefghijklmnopqrstuvwxyza" (rotate 1)))))

(deftest test-alpha-pos
  (testing "can find the zero-indexed element in the sequence"
    (is (= 2 (alpha-pos \c "arctic")))))

(deftest test-cipher-letter
  (testing "with two letters you can find the corresponding cipher letter"
    (is (= "p" (cipher-letter [\o \b])))))

(deftest test-match-keyword-length-to-message
  (testing "secret is repeated whole or part to match string to be encoded"
    (is (= "vietnamvietnamvie" (match-keyword-length-to-message "vietnam" "smellslikevictory")))))

(def expected-mapping
  (seq [[ \s \v ] [ \m \i ] [ \e \e ] [ \l \t ]
        [ \l \n ] [ \s \a ] [ \l \m ] [ \i \v ] [ \k \i ]
        [ \e \e ] [ \v \t ] [ \i \n ] [ \c \a ] [ \t \m ]
        [ \o \v ] [ \r \i ] [ \y \e ]]))

(def repeating
  (match-keyword-length-to-message "vietnam" "smellslikevictory"))

(deftest test-keyword-message->pairs
  (testing "secret and string are put into pairs"
    (is (= expected-mapping
           (keyword-message->pairs repeating "smellslikevictory")))))

(deftest test-encode
  (testing "can encode given a secret keyword"
    (is (= "hmkbxebpxpmyllyrxiiqtoltfgzzv"
           (encode "vigilance" "meetmeontuesdayeveningatseven")))
    (is (= "egsgqwtahuiljgs"
           (encode "scones" "meetmebythetree")))))

(deftest test-decode
  (testing "can decode an cyrpted message given a secret keyword"
    (is (= "meetmeontuesdayeveningatseven"
           (decode "vigilance" "hmkbxebpxpmyllyrxiiqtoltfgzzv")))
    (is (= "meetmebythetree"
           (decode "scones" "egsgqwtahuiljgs")))))
