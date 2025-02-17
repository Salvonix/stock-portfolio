import React, { useState, useEffect } from 'react';
import {getPortfolio, Portfolio, updatePortfolioPrices} from './services/api';
import PortfolioGrid from './components/PortfolioGrid';
import {
    TextField,
    Button,
    Container,
    Typography,
    Grid2,
    Dialog,
    DialogContent
} from '@mui/material';
import AddStockForm from "./components/AddStockForm";

const App: React.FC = () => {
    const [name, setName] = useState<string>('');
    const [portfolio, setPortfolio] = useState<Portfolio | null>(null);

    const [open, setOpen] = useState(false);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {
        if (portfolio) {
            const interval = setInterval(async () => {
                try {
                    const updatedPortfolio = await updatePortfolioPrices(portfolio.id);
                    setPortfolio(updatedPortfolio);
                } catch (error) {
                    console.error('Error updating prices:', error);
                }
            }, 1000);

            return () => clearInterval(interval);
        }
    }, [portfolio]);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setName(e.target.value);
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        getPortfolio(name).then((data) => setPortfolio(data));
    };

    return (
        <Container maxWidth="lg" sx={{ marginTop: '2rem' }}>
            <Typography variant="h3" gutterBottom align="center">
                Stock Portfolio
            </Typography>

            <form onSubmit={handleSubmit}>
                <Grid2 container spacing={2} justifyContent="center" alignItems="center">
                    <Grid2 sx={[{ xs: 12, sm: 6, md: 4 }]}>
                        <TextField
                            label="Enter Portfolio Owner"
                            variant="outlined"
                            value={name}
                            onChange={handleInputChange}
                            fullWidth
                        />
                    </Grid2>
                    <Grid2
                        sx={[{ xs: 12, sm: 6, md: 4 }]}>
                        <Button type="submit" variant="contained" color="primary" fullWidth>
                            Get Portfolio
                        </Button>
                    </Grid2>
                </Grid2>
            </form>

            {portfolio && (
                <div>
                    <PortfolioGrid portfolio={portfolio} />
                </div>

            )}


            {portfolio && (
                <div>
                    <Button variant="contained" color="primary" onClick={handleOpen}>
                        Add Stock to Portfolio
                    </Button>

                    <Dialog open={open} onClose={handleClose}>
                        <DialogContent>
                            <AddStockForm portfolio={portfolio} />
                        </DialogContent>
                    </Dialog>
                </div>
            )}

        </Container>
    );
};

export default App;