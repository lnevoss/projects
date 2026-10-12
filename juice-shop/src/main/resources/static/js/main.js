const PRODUCTS = {
  "golden-hour":    { name: "Golden Hour",    price: 6.5  },
  "midnight-berry": { name: "Midnight Berry", price: 7.0  },
  "garden-glow":    { name: "Garden Glow",    price: 6.75 },
  "ruby-root":      { name: "Ruby Root",      price: 7.25 },
  "soft-sunrise":   { name: "Soft Sunrise",   price: 7.5  },
  "violet-tonic":   { name: "Violet Tonic",   price: 8.0  },
  "pink-orchard":   { name: "Pink Orchard",   price: 6.75 },
  "oat-date":       { name: "Oat & Date",     price: 7.25 }
};

const CART_KEY = "violetpress-cart";

function getCart() {
  try { return JSON.parse(localStorage.getItem(CART_KEY)) || []; }
  catch (e) { return []; }
}

function saveCart(cart) {
  try { localStorage.setItem(CART_KEY, JSON.stringify(cart)); } catch (e) {}
  updateCartCount();
}

function euro(n) { return "€" + n.toFixed(2); }

function updateCartCount() {
  const total = getCart().reduce((sum, i) => sum + i.qty, 0);
  document.querySelectorAll(".cart-count").forEach(el => el.textContent = total);
}

function addToCart(name, qty) {
  qty = qty || 1;
  const slug = Object.keys(PRODUCTS).find(s => PRODUCTS[s].name === name);
  if (!slug) return;
  const cart = getCart();
  const line = cart.find(i => i.slug === slug);
  if (line) line.qty += qty; else cart.push({ slug: slug, qty: qty });
  saveCart(cart);
  showToast(name + " added to cart");
}

let toastTimer;
function showToast(text) {
  let el = document.querySelector(".toast");
  if (!el) {
    el = document.createElement("div");
    el.className = "toast";
    el.setAttribute("role", "status");
    document.body.appendChild(el);
  }
  el.textContent = text;
  el.classList.add("show");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => el.classList.remove("show"), 2200);
}

/* Cart page */
function renderCart() {
  const root = document.getElementById("cart-root");
  if (!root) return;
  const cart = getCart().filter(i => PRODUCTS[i.slug]);

  if (!cart.length) {
    root.innerHTML =
      '<div class="empty-state"><h2>Your cart is empty</h2>' +
      '<p>Pick a juice from the collection and it will show up here.</p>' +
      '<a href="index.html#shop" class="button button-dark">Browse juices</a></div>';
    return;
  }

  let subtotal = 0;
  const rows = cart.map(i => {
    const p = PRODUCTS[i.slug];
    const line = p.price * i.qty;
    subtotal += line;
    return '<li class="cart-line" data-slug="' + i.slug + '">' +
      '<div class="cart-thumb thumb-' + i.slug + '"></div>' +
      '<div class="cart-line-info"><a href="products/' + i.slug + '.html"><strong>' + p.name + '</strong></a>' +
      '<span>' + euro(p.price) + ' each</span></div>' +
      '<div class="qty" role="group" aria-label="Quantity for ' + p.name + '">' +
      '<button type="button" data-act="dec" aria-label="Decrease quantity">−</button>' +
      '<span>' + i.qty + '</span>' +
      '<button type="button" data-act="inc" aria-label="Increase quantity">+</button></div>' +
      '<strong class="cart-line-total">' + euro(line) + '</strong>' +
      '<button type="button" class="link-button" data-act="remove">Remove</button></li>';
  }).join("");

  root.innerHTML =
    '<div class="cart-layout"><ul class="cart-list">' + rows + '</ul>' +
    '<aside class="cart-summary"><h2>Summary</h2>' +
    '<div class="summary-row"><span>Subtotal</span><strong>' + euro(subtotal) + '</strong></div>' +
    '<div class="summary-row"><span>Delivery</span><span>Calculated at checkout</span></div>' +
    '<button type="button" class="button button-dark" id="checkout-btn">Go to checkout</button>' +
    '<p class="form-note" id="checkout-note" hidden>Checkout isn\'t connected yet. Hook this button up to your payment provider.</p>' +
    '</aside></div>';
}

document.addEventListener("click", function (e) {
  const btn = e.target.closest("[data-act]");
  if (btn && btn.closest(".cart-line")) {
    const slug = btn.closest(".cart-line").dataset.slug;
    let cart = getCart();
    const line = cart.find(i => i.slug === slug);
    if (!line) return;
    if (btn.dataset.act === "inc") line.qty++;
    if (btn.dataset.act === "dec") line.qty = Math.max(1, line.qty - 1);
    if (btn.dataset.act === "remove") cart = cart.filter(i => i.slug !== slug);
    saveCart(cart);
    renderCart();
    return;
  }
  if (e.target.id === "checkout-btn") {
    document.getElementById("checkout-note").hidden = false;
  }
  const qtyBtn = e.target.closest("[data-qty]");
  if (qtyBtn) {
    const out = document.getElementById("pdp-qty");
    let n = parseInt(out.textContent, 10) || 1;
    n = qtyBtn.dataset.qty === "inc" ? Math.min(20, n + 1) : Math.max(1, n - 1);
    out.textContent = n;
  }
  const pdpAdd = e.target.closest("[data-pdp-add]");
  if (pdpAdd) {
    addToCart(pdpAdd.dataset.pdpAdd, parseInt(document.getElementById("pdp-qty").textContent, 10) || 1);
  }
  const tab = e.target.closest("[data-tab]");
  if (tab) {
    document.querySelectorAll("[data-tab]").forEach(t => {
      const on = t === tab;
      t.classList.toggle("active", on);
      t.setAttribute("aria-selected", on);
    });
    document.querySelectorAll("[data-panel]").forEach(p => p.hidden = p.dataset.panel !== tab.dataset.tab);
  }
});

// TODO: replace
document.addEventListener("submit", function (e) {
  const form = e.target.closest("form[data-demo]");
  if (!form) return;
  e.preventDefault();
  const msg = form.querySelector(".form-message") || form.parentElement.querySelector(".form-message");
  if (msg) { msg.textContent = form.dataset.demo; msg.hidden = false; }
  else { showToast(form.dataset.demo); }
  if (form.dataset.reset !== "no") form.reset();
});

updateCartCount();
renderCart();
