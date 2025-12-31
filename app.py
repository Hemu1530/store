from flask import Flask, render_template, request, redirect, url_for, session

app = Flask(__name__)
app.secret_key = 'bnw_shopping_secret'

# Mock Product Data
PRODUCTS = [
    {'id': '1', 'name': 'Minimalist Tee', 'price': 25.00, 'image': '👕'},
    {'id': '2', 'name': 'Black Denim', 'price': 60.00, 'image': '👖'},
    {'id': '3', 'name': 'Generic Cap', 'price': 15.00, 'image': '🧢'},
    {'id': '4', 'name': 'Canvas Sneakers', 'price': 45.00, 'image': '👟'},
    {'id': '5', 'name': 'Tote Bag', 'price': 12.00, 'image': '👜'},
    {'id': '6', 'name': 'Sunglasses', 'price': 20.00, 'image': '🕶️'},
]

@app.route('/')
def index():
    cart = session.get('cart', [])
    return render_template('index.html', products=PRODUCTS, cart_size=len(cart))

@app.route('/add/<product_id>')
def add_to_cart(product_id):
    cart = session.get('cart', [])
    cart.append(product_id)
    session['cart'] = cart
    return redirect(url_for('index'))

@app.route('/cart')
def cart():
    cart_ids = session.get('cart', [])
    cart_items = []
    total = 0
    for pid in cart_ids:
        for p in PRODUCTS:
            if p['id'] == pid:
                cart_items.append(p)
                total += p['price']
                break
    return render_template('cart.html', cart=cart_items, total=total)

@app.route('/clear_cart')
def clear_cart():
    session.pop('cart', None)
    return redirect(url_for('cart'))

if __name__ == '__main__':
    app.run(debug=True)
