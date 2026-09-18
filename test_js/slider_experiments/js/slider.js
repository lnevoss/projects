window.addEventListener('load', init)

let slider;
let slider_cards;
let center_id;
let center_el;

let cards_raw = ["card1", "cards2", "card3"]
let cards_ext = cards_raw.concat(cards_raw.concat(cards_raw.concat(cards_raw.concat(cards_raw))))

let prev_scroll = 1;
const CARD_WIDTH = 300 * .8

function init() {
    slider = document.getElementById('card_slider');    
    create_cards()
    slider_cards = slider.children
    center_id = Math.floor(slider_cards.length/2);
    center_el = slider_cards[center_id]
    // center_el.classList.toggle('active')
    // console.log(center_el)
    slider_drag_scroll(slider)
}

function create_cards() {
    cards_ext.forEach(e => {
        var card = document.createElement('div')
        card.classList.add('card')
        slider.appendChild(card)
    })
    console.log(cards_ext)
}

function slider_drag_scroll(slider) {
    let isDown = false;
    let startX;
    let scrollLeft;

    slider.scrollLeft = CARD_WIDTH * (center_id+0.5);

    slider.addEventListener('mousedown', (e) => {
        isDown = true;
        startX = e.pageX - slider.offsetLeft;
        scrollLeft = slider.scrollLeft;
    });
    slider.addEventListener('mouseleave', () => {
        isDown = false; 
    });
    slider.addEventListener('mouseup', () => {
        isDown = false;
    });
    slider.addEventListener('mousemove', (e) => {
        if(!isDown) return;
        e.preventDefault();
        const x = e.pageX - slider.offsetLeft;
        const walk = (x - startX) * 3; //scroll-fast
        slider.scrollLeft = scrollLeft - walk;
    });
}
