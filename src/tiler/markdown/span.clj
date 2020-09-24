(ns tiler.markdown.span
  (:require [instaparse.core :as insta]))

(def parse
  (insta/parser
   "문장       := (링크 / 측주 / 강조 / #'[^\\[<*`_]+' / ANY)+
    (* 링크 *)
    <링크>     := 일반링크 / 자동링크 / 참조링크
    일반링크   := <'['> 링크텍스트 <']('> 주소 <')'>
    링크텍스트 := #'[^\\]]+'
    주소       := #'[^\\)]+'
    자동링크   := <'<'> #'.+://[^>]+' <'>'>
    참조링크   := <'['> 링크텍스트 <']' 공백? '['> 참조이름 <']'> / <'['> 참조이름 <'][]'>
    참조이름   := #'[^\\]]+'
    (* 각주 *)
    측주       := <'[>'> 참조이름 <']'>
    (* 강조 *)
    <강조>     := 굵게 / 기울임 / 코드
    굵게       := <'**'> ANY+ <'**'> / <'__'> ANY+ <'__'>
    기울임     := <'*'> ANY+ <'*'> / <'_'> ANY+ <'_'>
    코드       := <'``'> #'[^`]+' <'``'> / <'`'> #'[^`]+' <'`'>
    (* 이스케이프: 미처리 *)
    <eTXT>     := (ESC / ANY)+
    ESC        := <'\\\\'> escaped
    <escaped>  := #'[\\`*_{}\\[\\]()#+\\-.!]'
    <ANY>      := #'.'
    공백       := ' '
    <LF>       := '\\n'
   "))
