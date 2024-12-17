const express = require('express');
const pool = require('./db');
router = express.Router();

router.get('/', async (req, res) => {
    res.redirect('/birds')
});

router.get('/birds/create', async (req, res) => {
  
    conservation_status_data = []

    /* conservation status from mysql */
    const db = pool.promise();
    const status_query = `SELECT * FROM ConservationStatus;`
    try {
        const [rows, fields] = await db.query(status_query);
        conservation_status_data = rows;
    } catch (err) {
        console.error("You havent set up the database yet!");
    }


   
        res.render('create', { title: 'Birds of Aotearoa', status: conservation_status_data });

    /* REPLACE THE .json WITH A MYSQL DATABASE */





});

router.get('/birds', async (req, res) => {
    conservation_status_data = []

    /* conservation status from mysql */
    const db = pool.promise();
    const status_query = `SELECT * FROM ConservationStatus;`
    try {
        const [rows, fields] = await db.query(status_query);
        conservation_status_data = rows;
    } catch (err) {
        console.error("You havent set up the database yet!");
    }


    const bird_query = `SELECT * FROM Bird, Photos, ConservationStatus WHERE Bird.bird_id = Photos.bird_id and Bird.status_id = ConservationStatus.status_id;`
    try {
        const [rows, fields] = await db.query(bird_query);
        const birds = rows;
        /* bind data to the view (index.ejs) */
        res.render('index', { title: 'Birds of Aotearoa', birds: birds, status: conservation_status_data });
    } catch (err) {
        console.error("Bird error");
    }
    /* REPLACE THE .json WITH A MYSQL DATABASE */



});


router.get('/birds/:id/update', async (req, res) => {
    const birdId = req.params.id;

    conservation_status_data = []

    /* conservation status from mysql */
    const db = pool.promise();
    const status_query = `SELECT * FROM ConservationStatus;`
    try {
        const [rows, fields] = await db.query(status_query);
        conservation_status_data = rows;
    } catch (err) {
        console.error("You havent set up the database yet!");
    }


    const bird_query = `SELECT * FROM Bird, Photos, ConservationStatus WHERE Bird.bird_id = Photos.bird_id and Bird.status_id = ConservationStatus.status_id;`
    try {
        const [rows, fields] = await db.query(bird_query);
        const birds = rows.filter(bird => bird.bird_id == birdId);
        /* bind data to the view (index.ejs) */
        res.render('update', { title: 'Birds of Aotearoa', bird: birds[0], status: conservation_status_data });
    } catch (err) {
        console.error("Bird error");
    }
    /* REPLACE THE .json WITH A MYSQL DATABASE */



});

router.get('/birds/:id/delete', async (req, res) => {
    const birdId = req.params.id;
    const photo_query = `DELETE FROM Photos WHERE bird_id = ?;`
    const bird_query = `DELETE FROM Bird WHERE bird_id = ?;`
    const db = pool.promise();
    try {
        const [rowsPhoto, fieldsPhoto] = await db.query(photo_query, [birdId]);
        const [rows, fields] = await db.query(bird_query, [birdId]);
    } catch (err) {
        console.error("Bird error"+birdId);
    }


    res.redirect('/birds')
    
    /* REPLACE THE .json WITH A MYSQL DATABASE */



});

router.get('/birds/:id', async (req, res) => {
    const birdId = req.params.id;

    conservation_status_data = []

    /* conservation status from mysql */
    const db = pool.promise();
    const status_query = `SELECT * FROM ConservationStatus;`
    try {
        const [rows, fields] = await db.query(status_query);
        conservation_status_data = rows;
    } catch (err) {
        console.error("You havent set up the database yet!");
    }

    const bird_query = `SELECT * FROM Bird, Photos, ConservationStatus WHERE Bird.bird_id = Photos.bird_id and Bird.status_id = ConservationStatus.status_id;`;
    try {
        const [rows, fields] = await db.query(bird_query);
        const birds = rows.filter(bird => bird.bird_id == birdId);

        if (birds.length === 0) {
            // No bird found with the specified ID, trigger a 404 error
            res.status(404);
            res.redirect('/404');
        } 
        /* bind data to the view (index.ejs) */
        res.render('view', { title: 'Birds of Aotearoa', birds: birds, status: conservation_status_data });
    } catch (err) {
        console.log(err);
       
    }
    /* REPLACE THE .json WITH A MYSQL DATABASE */



});




const fileUpload = require('express-fileupload'); // Import express-fileupload
const bodyParser = require('body-parser'); //import body parser peepeepoopoo
const fs = require('fs').promises;

// Use body-parser middleware
router.use(bodyParser.urlencoded({ extended: false }));
router.use(bodyParser.json());

// Use express-fileupload middleware defaultoptions
router.use(fileUpload());

router.post('/birds/edit', async (req, res) => {
    const formData = req.body;

    const db = pool.promise();
    const updateQuery = `
        UPDATE Bird
        SET primary_name = ?, 
            english_name = ?, 
            scientific_name = ?, 
            order_name = ?, 
            family = ?, 
            weight = ?, 
            length = ?, 
            status_id = ?
        WHERE bird_id = ?;
`;
    const updatePhotoQuery = `
        UPDATE Photos
        SET filename = ?,
            photographer = ?
        WHERE bird_id = ?;
        `;

    try {
        const [updateResult, updateFields] = await db.query(updateQuery, [
            formData.primary_name,
            formData.english_name,
            formData.scientific_name,
            formData.order_name,
            formData.family,
            formData.weight,
            formData.length,
            formData.status_id,
            formData.bird_id
        ]);
        /*  de file woohoo */
        const ifFile = req.files;
        let photo_name = formData.photo_source.replace('/images/', '');
        if (ifFile != null) {
            photo_name = req.files.photo_upload.name;
        }

        const [updateImageResult, updateImageFields] = await db.query(updatePhotoQuery, [
            photo_name,
            formData.photographer,
            formData.bird_id
            
        ]);

      
        if (ifFile != null){
            // Specify the path where the file should be saved
            const filePath = __dirname + '/public/images/' + req.files.photo_upload.name;
            // Save the file using fs.writeFile
            fs.writeFile(filePath, req.files.photo_upload.data);
        }

     
        // Successful update
        res.redirect('/birds')
    } catch (err) {
        // Handle the error appropriately
        res.status(500).send("An error occurred while updating the bird" + err);
    }


});
router.post('/birds/create', async (req, res) => {
    const formData = req.body; // Assuming you're getting bird data from a form
    const photo = req.files.photo_upload; // Assuming you're getting photo data from a form
    
    const db = pool.promise();

    try {
        // Insert bird data
        const birdQuery = `INSERT INTO Bird (primary_name, english_name, scientific_name, order_name, family, weight, length, status_id)
                           VALUES (?, ?, ?, ?, ?, ?, ?, ?);`;
        const [birdResult] = await db.query(birdQuery, [      
            formData.primary_name,
            formData.english_name,
            formData.scientific_name,
            formData.order_name,
            formData.family,
            formData.weight,
            formData.length,
            formData.status_id]);
        
        const birdId = birdResult.insertId; // Retrieve the generated bird_id
        
        // Insert photos data using the retrieved bird_id
       
            const photoQuery = `INSERT INTO Photos (bird_id, filename, photographer)
                                VALUES (?, ?, ?);`;  
            await db.query(photoQuery, [birdId, photo.name, formData.photographer]);
    

         // Specify the path where the file should be saved
         const filePath = __dirname + '/public/images/' + photo.name;
         // Save the file using fs.writeFile
         fs.writeFile(filePath, photo.data);
        
        res.redirect('/birds');
    } catch (err) {
        console.error("Error inserting data:", err);
        res.status(500).send("Error inserting data");
    }
});

router.get('/404', async (request, response) => {

    /* conservation status from mysql */
    const db = pool.promise();
    const status_query = `SELECT * FROM ConservationStatus;`
    try {
        const [rows, fields] = await db.query(status_query);
        conservation_status_data = rows;shoe/0
    } catch (err) {
        console.error("You havent set up the database yet!");
    }


    const bird_query = `SELECT * FROM Bird, Photos, ConservationStatus WHERE Bird.bird_id = Photos.bird_id and Bird.status_id = ConservationStatus.status_id;`
    try {
        const [rows, fields] = await db.query(bird_query);
        const birds = rows;
         response.status(404);
        response.render('404-page', { title: 'Birds of Aotearoa', birds: birds, status: conservation_status_data });
    } catch (err) {
        console.error("Bird error");
    }
  

});
router.get('*', async (request, response) => {

     response.redirect('/404');
   

});
module.exports = router;
