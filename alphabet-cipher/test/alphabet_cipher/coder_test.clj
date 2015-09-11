(ns alphabet-cipher.coder-test
  (:require [clojure.test :refer :all]
            [alphabet-cipher.coder :refer :all]))

(deftest test-rotate
  (testing "can rotate the alpha sequence"
    (is (= "BCDEFGHIJKLMNOPQRSTUVWXYZA" (rotate 1)))))

(deftest test-alpha-pos
  (testing "can find the zero-indexed element in the sequence"
    (is (= 2 (alpha-pos \C "ARCTIC")))))

(deftest test-cipher-letter
  (testing "with two letters you can find the corresponding cipher letter"
    (is (= "P" (cipher-letter [\O \B])))))

(deftest test-match-keyword-length-to-message
  (testing "secret is repeated whole or part to match string to be encoded"
    (is (= "vietnamvietnamvie" (match-keyword-length-to-message "vietnam" "smellslikevictory")))))

(def expected-mapping
  (seq [[ \S \V ] [ \M \I ] [ \E \E ] [ \L \T ]
        [ \L \N ] [ \S \A ] [ \L \M ] [ \I \V ] [ \K \I ]
        [ \E \E ] [ \V \T ] [ \I \N ] [ \C \A ] [ \T \M ]
        [ \O \V ] [ \R \I ] [ \Y \E ]]))

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
