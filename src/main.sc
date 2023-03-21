
require: text/text.sc
    module = zenbot-common
    
require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common

require: hangmanGameData.csv
    name = hangmanGameData
    var = $hangmanGameData

patterns:
    $Word = $entity<hangmanGameData> || converter = function ($parseTree) {
        var id = $parseTree.hangmanGameData[0].value;
        return $hangmanGameData[id].value;
        };

theme: /

    state: Start
        q!: $regex</start>
        a: Привет.

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

