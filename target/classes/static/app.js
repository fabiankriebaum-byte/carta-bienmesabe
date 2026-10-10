/* Ampliar foto al tocarla */
document.addEventListener("click", e => {
  if (e.target.matches(".item img")) {
    const v = document.createElement("div");
    v.className = "visor";
    v.innerHTML = `<img src="${e.target.src}" alt="${e.target.alt}">`;
    v.onclick = () => v.remove();
    document.body.appendChild(v);
  }
});

document.querySelector("[data-close-notice]")?.addEventListener("click", () => {
  document.querySelector("#daily-notice")?.remove();
});

/* Resalta la categoría visible */
const links = [...document.querySelectorAll(".cats a")];
const obs = new IntersectionObserver(es => es.forEach(en => {
  if (en.isIntersecting) {
    links.forEach(l => l.classList.toggle("on", l.getAttribute("href") === "#" + en.target.id));
    document.querySelector(".cats a.on")?.scrollIntoView({ inline: "center", block: "nearest" });
  }
}), { rootMargin: "-20% 0px -70% 0px" });
document.querySelectorAll("main section").forEach(s => obs.observe(s));
