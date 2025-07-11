// vhs.js
(() => {
  const style = document.createElement('style');
  style.textContent = `
    .vhs-scanlines {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      pointer-events: none;
      background: repeating-linear-gradient(
        to bottom,
        rgba(0, 0, 0, 0) 0px,
        rgba(0, 0, 0, 0) 2px,
        rgba(0, 0, 0, 0.1) 2px,
        rgba(0, 0, 0, 0.1) 3px
      );
      animation: scanlineAnimation 0.5s linear infinite;
    }
    
    @keyframes scanlineAnimation {
      from { background-position: 0 0; }
      to { background-position: 0 3px; }
    }
  `;
  document.head.appendChild(style);

  const scanlines = document.createElement('div');
  scanlines.classList.add('vhs-scanlines');
  document.body.appendChild(scanlines);
})();