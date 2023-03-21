require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: text/text.sc
    module = zenbot-common
    
require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common

require: cities-ru.csv
    name = cities-ru
    var = $cities-ru

patterns:
    $Word = $entity<cities-ru> || converter = function ($parseTree) {
        var id = $parseTree.cities-ru[0].value;
        return $cities-ru[id].value;
        };

theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.

    state: CityPattern
        q: * $City *
        a: Город: {{$parseTree._City.name}}
        
    state: Text
        q: $Word
        a: Слово из справочника: {{$parseTree._Word.word}}

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

    state: reset
        q!: reset
        script:
            $session = {};
            $client = {};
        go!: /

