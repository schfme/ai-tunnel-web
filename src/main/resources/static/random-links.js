document.addEventListener("DOMContentLoaded", () => {
	if (!window.randomStartingPoints || !Array.isArray(window.randomStartingPoints)) return;

	const list = document.getElementById("random-links-list");
	window.randomStartingPoints.forEach(word => {
		const li = document.createElement("li");
		const a = document.createElement("a");
		a.href = `/${word}`;
		a.textContent = `/${word}`;
		a.className = "hover-link";
		a.rel = "nofollow";
		li.appendChild(a);
		list.appendChild(li);
	});
});
