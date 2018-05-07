<%@include file="../appGlobal/pageTop.jsp"%>
<%--<%run(new com.fz.tms.params.test());%>--%>

<!DOCTYPE html>
<html>
<body>

<p>Click the button to replace "Microsoft" with "W3Schools" in the paragraph below:</p>

<p id="demo">{"rows":[{"elements":[{"distance":{"text":"3.1 mi","value":4940},"duration":{"text":"15 mins","value":872},"status":"OK"},{"distance":{"text":"8.4 mi","value":13485},"duration":{"text":"20 mins","value":1222},"status":"OK"},{"distance":{"text":"6.6 mi","value":10590},"duration":{"text":"27 mins","value":1599},"status":"OK"},{"distance":{"text":"7.2 mi","value":11519},"duration":{"text":"35 mins","value":2111},"status":"OK"},{"distance":{"text":"15.7 mi","value":25290},"duration":{"text":"36 mins","value":2171},"status":"OK"},{"distance":{"text":"21.3 mi","value":34278},"duration":{"text":"50 mins","value":3002},"status":"OK"},{"distance":{"text":"3.5 mi","value":5583},"duration":{"text":"15 mins","value":895},"status":"OK"},{"distance":{"text":"5.9 mi","value":9562},"duration":{"text":"27 mins","value":1626},"status":"OK"},{"distance":{"text":"7.6 mi","value":12220},"duration":{"text":"29 mins","value":1756},"status":"OK"},{"distance":{"text":"15.1 mi","value":24373},"duration":{"text":"28 mins","value":1707},"status":"OK"},{"distance":{"text":"24.4 mi","value":39300},"duration":{"text":"55 mins","value":3283},"status":"OK"},{"distance":{"text":"19.2 mi","value":30971},"duration":{"text":"46 mins","value":2767},"status":"OK"},{"distance":{"text":"9.9 mi","value":15854},"duration":{"text":"41 mins","value":2474},"status":"OK"},{"distance":{"text":"7.7 mi","value":12334},"duration":{"text":"31 mins","value":1879},"status":"OK"}]}],"originAddresses":["Jl. Komarudin No.27C, Penggilingan, Cakung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13940, Indonesia"],"destinationAddresses":["Jl. Bintara Niaga Blok II1 No.4, Bintara, Bekasi Bar., Kota Bks, Jawa Barat 17134, Indonesia","Jl. Raya Jati Asih No.9, Jatirasa, Jatiasih, Kota Bks, Jawa Barat 17424, Indonesia","Jl. Keamanan No.15, Pd. Bambu, Duren Sawit, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13430, Indonesia","Lotte Mart, Jl. Boulevard Bar. Raya, Klp. Gading Bar., Klp. Gading, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14240, Indonesia","Jl. Mega Kuningan Barat No.3, Kuningan, Kuningan Tim., Kecamatan Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12950, Indonesia","Jl. Duta Harapan Indah, Kapuk Muara, Penjaringan, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14460, Indonesia","Jl. I Gusti Ngurah Rai No.127, Bintara, Bekasi Bar., Kota Bks, Jawa Barat 17134, Indonesia","Jl. Mandiri Tengah, Klp. Gading Tim., Klp. Gading, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14240, Indonesia","Jl. Kb. Jeruk Tim. 1 No.26, Cipinang Besar Utara, Jatinegara, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13410, Indonesia","East Jakarta, East Jakarta City, Jakarta, Indonesia","Jl. Pantai Indah Kapuk, Kamal Muara, Penjaringan, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14470, Indonesia","Jl. Ancol Barat VIII No.2, Kota Tua, Ancol, Pademangan, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14430, Indonesia","Jl. Trembesi, Kota Tua, Pademangan Tim., Pademangan, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta 14350, Indonesia","Pintu Utama No.24, Bali Mester, Jatinegara, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13310, Indonesia"]}</p>

<button onclick="myFunction()">Try it</button>

<script>
function myFunction() {
    var str = document.getElementById('demo').innerHTML; 
    var res = str.replace(/"/g, '\\"');
    document.getElementById('demo').innerHTML = res;
}
</script>

</body>
</html>
