
require: text/text.sc
    module = zenbot-common
    
require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common

require: cities-ru.csv
    name = name
    var = $name
    
require: cities-ru.csv
    name = country
    var = $country
    
    
    
theme: /

    state: Rules
        q!: $regex</start>
        intent!: /LetsPlay
        a: Let's play Guess Number game. I think of a counry you guess its capital. Ready to start?
        go!: /Rules/Agree

        state: Agree

            state: Yes
                intent: /Yes
                go!: /Game

            state: No
                intent: /No
                a: That's a pity! If you change your mind, just text "Lets's play"
    
    state: Game
        script:
            $session.country = Object.country($country);
            var country = country[chooseRandcountryKey($session.keys)].value.name
            $reactions.transition("/CheckCapital")
            
    state: CheckCapital
        intent: /Capital
        script:
            var Capital = $parseTree._Capital;
            if (Capital == $session.Capital) {
                $reactions.answer("You won! Let's play one more time?");
                $reactions.transition("/Rules/Agree");
            }
            else /endThisGame


