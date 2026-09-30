import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";
import productImages from "./assets/Images";

const API = "http://localhost:8080/product";

function App() {
  const [products, setProducts] = useState([]);
  const [popular, setPopular] = useState([]);
  const [recent, setRecent] = useState([]);

  // =========================
  // LOADing DATA
  // =========================
  const loadData = async () => {
    try {
      const productsRes = await axios.get(API);
      setProducts(productsRes.data);
    } catch (error) {
      console.error("Products error:", error);
    }

    try {
      const popularRes = await axios.get(`${API}/popular`);
      setPopular(popularRes.data);
    } catch (error) {
      console.error("Popular products error:", error);
      setPopular([]);
    }

    try {
      const recentRes = await axios.get(`${API}/recent`);
      setRecent(recentRes.data);
    } catch (error) {
      console.error("Recent products error:", error);
      setRecent([]);
    }
  };

  useEffect(() => {
    loadData();
  }, []);


  const openProduct = async (id) => {
    try {
      const response = await axios.get(`${API}/${id}`);

      alert(
        `Product: ${response.data.name}\nPrice: ₹${response.data.price.toLocaleString(
          "en-IN"
        )}`
      );

      loadData();
    } catch (error) {
      console.error("Product error:", error);
    }
  };

  const getImage = (id) => {
    return (
      productImages[id] ||
      "https://images.unsplash.com/photo-1496181133206-80ce9b88a853"
    );
  };

  return (
    <div className="app">
      <div className="background-lines"></div>

      <nav className="navbar">
        <div className="logo">
          <span className="logo-mark">✦</span>
          ELECTRONICS <span>STORE</span>
        </div>

        <div className="nav-links">
          <a href="#home">Home</a>
          <a href="#popular">Popular</a>
          <a href="#recent">Recent</a>
          <a href="#products">Products</a>
        </div>

        <button
          className="nav-button"
          onClick={() =>
            document
              .getElementById("products")
              ?.scrollIntoView({ behavior: "smooth" })
          }
        >
          EXPLORE →
        </button>
      </nav>

      {/* HERO */}
      <section className="hero" id="home">
        <div className="hero-content">
          <div className="hero-badge">● SPRING BOOT + REDIS</div>

          <h1>
            Smart product
            <br />
            <span>management.</span>
          </h1>

          <p>
            A modern product platform powered by Spring Boot,
            MySQL and Redis caching.
          </p>

          <div className="hero-buttons">
            <button
              className="primary-button"
              onClick={() =>
                document
                  .getElementById("products")
                  ?.scrollIntoView({ behavior: "smooth" })
              }
            >
              VIEW PRODUCTS →
            </button>

            <button
              className="secondary-button"
              onClick={() =>
                document
                  .getElementById("popular")
                  ?.scrollIntoView({ behavior: "smooth" })
              }
            >
              POPULAR PRODUCTS
            </button>
          </div>
        </div>

        {/* ORBIT */}
        <div className="hero-orbit">
          <div className="orbit orbit-one"></div>
          <div className="orbit orbit-two"></div>
          <div className="orbit orbit-three"></div>

          <div className="hero-core">
            <span>REDIS</span>
          </div>
        </div>
      </section>

      <section className="stats">
        <div>
          <strong>{products.length}</strong>
          <span>PRODUCTS</span>
        </div>

        <div>
          <strong>{popular.length}</strong>
          <span>POPULAR</span>
        </div>

        <div>
          <strong>{recent.length}</strong>
          <span>RECENT VIEWS</span>
        </div>

        <div>
          <strong>REDIS</strong>
          <span>POWERED</span>
        </div>
      </section>

      <section className="section" id="popular">
        <div className="section-heading">
          <div>
            <span className="section-label">01 — TRENDING</span>
            <h2>Popular Products</h2>
          </div>

          <p>
            Products with the highest number of views,
            tracked using Redis Sorted Sets.
          </p>
        </div>

        <div className="product-grid">
          {popular.length > 0 ? (
            popular.map((product, index) => (
              <ProductCard
                key={product.id}
                product={product}
                image={getImage(product.id)}
                rank={index + 1}
                openProduct={openProduct}
              />
            ))
          ) : (
            <div className="empty">No popular products yet.</div>
          )}
        </div>
      </section>

      {/* RECENTLY VIEWED */}
      <section className="section recent-section" id="recent">
        <div className="section-heading">
          <div>
            <span className="section-label">02 — ACTIVITY</span>
            <h2>Recently Viewed</h2>
          </div>

          <p>
            Your latest product activity tracked with Redis Lists.
          </p>
        </div>

        <div className="recent-grid">
          {recent.length > 0 ? (
            recent.map((id, index) => {
              const product = products.find(
                (item) => item.id === Number(id)
              );

              return (
                <div
                  className="recent-card"
                  key={`${id}-${index}`}
                  onClick={() => openProduct(id)}
                >
                  <img
                    src={getImage(id)}
                    alt={product?.name || `Product ${id}`}
                    onError={(e) => {
                      e.currentTarget.src =
                        "https://images.unsplash.com/photo-1496181133206-80ce9b88a853";
                    }}
                  />

                  <div>
                    <span>RECENT #{index + 1}</span>

                    <h3>
                      {product?.name || `Product #${id}`}
                    </h3>
                  </div>

                  <strong>→</strong>
                </div>
              );
            })
          ) : (
            <div className="empty">
              No recently viewed products.
            </div>
          )}
        </div>
      </section>

      <section className="section" id="products">
        <div className="section-heading">
          <div>
            <span className="section-label">03 — CATALOG</span>
            <h2>All Products</h2>
          </div>

          <p>Explore the complete product catalog.</p>
        </div>

        <div className="product-grid">
          {products.map((product) => (
            <ProductCard
              key={product.id}
              product={product}
              image={getImage(product.id)}
              openProduct={openProduct}
            />
          ))}
        </div>
      </section>

      {/* FOOTER */}
      <footer>
        <div className="logo">
          <span className="logo-mark">✦</span>
          ELECTRONICS <span>STORE</span>
        </div>

        <p>
          Built with React • Spring Boot • MySQL • Redis
        </p>
      </footer>
    </div>
  );
}
// PRODUCT CARD


function ProductCard({
  product,
  image,
  openProduct,
  rank,
}) {
  return (
    <div className="product-card">
      <div className="image-container">
        <img
          src={image}
          alt={product.name}
          onError={(e) => {
            e.currentTarget.src =
              "https://images.unsplash.com/photo-1496181133206-80ce9b88a853";
          }}
        />

        {rank && (
          <span className="rank">
            #{rank}
          </span>
        )}
      </div>

      <div className="product-info">
        <span className="product-id">
          PRODUCT #{product.id}
        </span>

        <h3>{product.name}</h3>

        <div className="product-bottom">
          <strong>
            ₹{product.price.toLocaleString("en-IN")}
          </strong>

          <button
            onClick={() => openProduct(product.id)}
          >
            VIEW →
          </button>
        </div>
      </div>
    </div>
  );
}

export default App;