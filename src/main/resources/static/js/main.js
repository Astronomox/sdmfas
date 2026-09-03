function labelize(enumVal) {
  return enumVal.replace(/_/g, ' ').replace(/\w\S*/g, w => w[0] + w.slice(1).toLowerCase());
}

async function loadLgasInto(selectId) {
  const res = await fetch('/api/lgas');
  const lgas = await res.json();
  const select = document.getElementById(selectId);
  select.innerHTML = lgas.map(l => `<option value="${l}">${labelize(l)}</option>`).join('');
}
